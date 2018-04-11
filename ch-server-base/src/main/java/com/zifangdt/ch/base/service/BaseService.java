package com.zifangdt.ch.base.service;

import com.google.common.base.CaseFormat;
import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.converter.NameGenerator;
import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dao.ManyToManyRelation;
import com.zifangdt.ch.base.dao.OneToManyRelation;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.util.ClassUtil;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.base.util.UserNameHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2017/9/12.
 */
@Transactional
public class BaseService<T extends BaseEntity<K>, K> implements ApplicationContextAware {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final Class<T> ENTITY_CLASS;
    private final Class<K> ID_CLASS;
    private Class<? extends BaseMapper<T>> MAPPER_CLASS;
    @Autowired
    private BaseMapper<T> mapper;

    private static ApplicationContext context;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private Method METHOD_GET_DETAIL;
    private final Set<OneToManyData> SET_ONE_TO_MANY;
    private final Set<ManyToManyData> SET_MANY_TO_MANY;

    @Autowired
    private UaaServerApi uaaServerApi;

    @PostConstruct
    public void init() {

        for (Class<?> mapperClass : sqlSessionFactory.getConfiguration().getMapperRegistry().getMappers()) {
            if (BaseMapper.class.isAssignableFrom(mapperClass)) {
                if (ClassUtil.getFirstSuperInterfaceActualTypes(mapperClass) == ENTITY_CLASS) {
                    MAPPER_CLASS = (Class<? extends BaseMapper<T>>) mapperClass;
                    break;
                }
            }
        }
        if (MAPPER_CLASS == null) {
            throw new RuntimeException("找不到对应的MapperClass");
        }
        METHOD_GET_DETAIL = ClassUtil.selfMethod(MAPPER_CLASS, "getDetail");

        for (Field field : ENTITY_CLASS.getDeclaredFields()) {
            String capitalizeFieldName = StringUtils.capitalize(field.getName());

            OneToManyRelation oneToMany = field.getAnnotation(OneToManyRelation.class);
            if (oneToMany != null) {
                Method getter = ClassUtil.selfMethod(ENTITY_CLASS, "get" + capitalizeFieldName);

                Class<? extends BaseEntity<?>> targetClass = (Class<? extends BaseEntity<?>>) ClassUtil.getFieldActualType(field);

                Class<?> idClass = ClassUtil.getSuperclassActualTypes(targetClass)[0];

                BaseMapper<? extends BaseEntity<?>> targetMapper = (BaseMapper<? extends BaseEntity<?>>) context.getBean(StringUtils.uncapitalize(targetClass.getSimpleName()) + "Mapper");
                String key = oneToMany.value();
                if (StringUtils.isEmpty(key)) {
                    key = StringUtils.uncapitalize(ENTITY_CLASS.getSimpleName()) + "Id";
                }
                Method targetKeySetter = ClassUtil.selfMethod(targetClass, "set" + StringUtils.capitalize(key));

                SET_ONE_TO_MANY.add(new OneToManyData(field.getName(), getter, targetClass, idClass, targetMapper, key, targetKeySetter));
            }

            ManyToManyRelation manyToMany = field.getAnnotation(ManyToManyRelation.class);
            if (manyToMany != null) {
                String selfKeyColumn = manyToMany.selfKeyColumn();
                if (StringUtils.isEmpty(selfKeyColumn)) {
                    selfKeyColumn = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, ENTITY_CLASS.getSimpleName()) + "_id";
                }
                String targetKeyColumn = manyToMany.targetKeyColumn();

                Method getter = ClassUtil.selfMethod(ENTITY_CLASS, "get" + capitalizeFieldName);

                Class<?> targetClass = ClassUtil.getFieldActualType(field);

                Class<?> idClass = null;
                String targetTable = null;
                boolean relatedEntity = true;
                if (BaseEntity.class.isAssignableFrom(targetClass)) {
                    idClass = ClassUtil.getSuperclassActualTypes(targetClass)[0];
                    if (StringUtils.isEmpty(targetKeyColumn)) {
                        targetKeyColumn = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, targetClass.getSimpleName()) + "_id";
                    }
                    targetTable = targetClass.getDeclaredAnnotation(Table.class).name();
                } else {
                    relatedEntity = false;
                    idClass = targetClass;
                    if (StringUtils.isEmpty(targetKeyColumn)) {
                        throw new RuntimeException(ENTITY_CLASS.getSimpleName() + "#" + field.getName() + "使用@ManyToManyRelation时必须指定targetKeyColumn属性");
                    }
                }

                SET_MANY_TO_MANY.add(new ManyToManyData(relatedEntity, field.getName(), idClass, getter, selfKeyColumn, targetKeyColumn, manyToMany.value(), targetTable));
            }
        }
    }

    public BaseService() {
        Class<?>[] classes = ClassUtil.getSuperclassActualTypes(this.getClass());
        ENTITY_CLASS = (Class<T>) classes[0];
        ID_CLASS = (Class<K>) classes[1];

        SET_ONE_TO_MANY = new HashSet<>();
        SET_MANY_TO_MANY = new HashSet<>();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    private class OneToManyData<A extends BaseEntity<B>, B> {
        final String fieldName;
        final Method getter;
        final Class<A> targetClass;
        final BaseMapper<A> targetMapper;
        final String key;
        final Method targetKeySetter;
        final Class<B> targetIdClass;

        public OneToManyData(String fieldName, Method getter, Class<A> targetClass, Class<B> targetIdClass, BaseMapper<A> targetMapper, String key, Method targetKeySetter) {
            this.fieldName = fieldName;
            this.getter = getter;
            this.targetClass = targetClass;
            this.targetMapper = targetMapper;
            this.key = key;
            this.targetKeySetter = targetKeySetter;
            this.targetIdClass = targetIdClass;
        }

        /**
         * 如果当前实体类有一对多的关联字段（使用@OneToManyRelation标注），则在update当前实体类时对该关联字段进行级联update
         *
         * @param toBeUpdated
         * @param id
         */
        public void updateRelation(T toBeUpdated, K id) {
            //获取前端提交的关联对象
            List<A> toBeUpdatedList = null;
            try {
                toBeUpdatedList = (List<A>) getter.invoke(toBeUpdated);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (toBeUpdatedList == null) {
                toBeUpdatedList = new ArrayList<>();
            }

            //根据外键查询数据库中已持久化的所有对象
            Example example = new Example(targetClass);
            example.createCriteria().andEqualTo(key, id);
            List<A> storedList = targetMapper.selectByExample(example);
            Map<B, A> storedMap = new HashMap<>();
            for (A e : storedList) {
                storedMap.put(e.getId(), e);
            }

            //对前端提交的关联对象分组，若id为null则表示需要insert，若id不为null则表示需要update
            Map<Boolean, List<A>> toBeUpdatedListMap = toBeUpdatedList.stream().collect(Collectors.partitioningBy(o -> o.getId() == null));

            List<B> receivedIds = toBeUpdatedListMap.get(Boolean.FALSE).stream().map(BaseEntity::getId).collect(Collectors.toList());
            List<B> storedIds = new ArrayList<>(storedMap.keySet());
            //从已经持久化的关联对象中去掉前端提交的部分，剩下的则表示需要delete
            storedIds.removeAll(receivedIds);
            if (!storedIds.isEmpty()) {
                Example deleteExample = new Example(targetClass);
                deleteExample.createCriteria().andIn("id", storedIds);
                targetMapper.deleteByExample(deleteExample);
            }

            //对id为null的部分关联对象进行insert
            List<A> inserts = toBeUpdatedListMap.get(Boolean.TRUE);
            if (!inserts.isEmpty()) {
                Long currentUserId = CurrentUser.getUserId();
                Date now = new Date();
                for (A target : inserts) {
                    if (target instanceof AuditEntity<?>) {
                        AuditEntity<?> ae = (AuditEntity<?>) target;
                        ae.setCreateId(currentUserId);
                        ae.setCreateTime(now);
                    }
                    try {
                        targetKeySetter.invoke(target, id);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                targetMapper.insertList(inserts);
            }

            //对id不为null的部分关联对象进行update
            for (A received : toBeUpdatedListMap.get(Boolean.FALSE)) {
                try {
                    //将外键字段设为null，这样即使前端提交的参数中修改了该字段值，也不会被update
                    targetKeySetter.invoke(received, new Object[]{null});
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                A storedTarget = storedMap.get(received.getId());
                targetMapper.updateAfterComparing(storedTarget, received);
            }
        }
    }

    private class ManyToManyData {
        final String fieldName;
        final Method getter;
        final String selfKeyColumn;
        final String targetKeyColumn;
        final String middleTable;
        final String targetTable;
        final Class<?> targetIdClass;
        final String sqlFindStored;
        final String sqlUnAssociate;
        final String sqlAssociate;
        final boolean relatedEntity;

        public ManyToManyData(boolean relatedEntity, String fieldName, Class<?> targetIdClass, Method getter, String selfKeyColumn, String targetKeyColumn, String middleTable, String targetTable) {
            this.relatedEntity = relatedEntity;
            this.fieldName = fieldName;
            this.getter = getter;
            this.selfKeyColumn = selfKeyColumn;
            this.targetKeyColumn = targetKeyColumn;
            this.middleTable = middleTable;
            this.targetTable = targetTable;
            this.targetIdClass = targetIdClass;
            if (!relatedEntity) {
                sqlFindStored = String.format("select %s from %s where %s=?", targetKeyColumn, middleTable, selfKeyColumn);
            } else {
                sqlFindStored = String.format("SELECT a.id FROM %s a LEFT JOIN %s b ON a.id=b.%s WHERE b.%s=?", targetTable, middleTable, targetKeyColumn, selfKeyColumn);
            }

            sqlUnAssociate = String.format("delete from %s where %s=? and %s=?", middleTable, selfKeyColumn, targetKeyColumn);
            sqlAssociate = String.format("insert into %s(%s,%s) values(?,?)", middleTable, selfKeyColumn, targetKeyColumn);
        }

        /**
         * 如果当前实体类有多对多的关联字段（使用@ManyToManyRelation标注），则在update当前实体类时对中间表进行级联update
         *
         * @param updated
         * @param id
         */
        public void updateRelation(T updated, K id) {
            List<?> receivedIds = null;
            if (!relatedEntity) {
                try {
                    receivedIds = (List<?>) getter.invoke(updated);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (receivedIds == null) {
                    receivedIds = new ArrayList<>();
                }
            } else {
                List<? extends BaseEntity<?>> toBeUpdatedList = null;
                try {
                    toBeUpdatedList = (List<? extends BaseEntity<?>>) getter.invoke(updated);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (toBeUpdatedList == null) {
                    toBeUpdatedList = new ArrayList<>();
                }
                receivedIds = toBeUpdatedList.stream().map(BaseEntity::getId).collect(Collectors.toList());
            }

            List<?> storedIds = jdbcTemplate.queryForList(sqlFindStored, targetIdClass, id);
            List<?> deletes = new ArrayList<>(storedIds);
            deletes.removeAll(receivedIds);
            if (!deletes.isEmpty()) {
                jdbcTemplate.batchUpdate(sqlUnAssociate, deletes.stream().map(targetId -> new Object[]{id, targetId}).collect(Collectors.toList()));
            }
            List<?> inserts = new ArrayList<>(receivedIds);
            inserts.removeAll(storedIds);
            if (!inserts.isEmpty()) {
                jdbcTemplate.batchUpdate(sqlAssociate, inserts.stream().map(targetId -> new Object[]{id, targetId}).collect(Collectors.toList()));
            }
        }

        /**
         * 如果当前实体类有多对多的关联字段（使用@ManyToManyRelation标注），则在save当前实体类时对中间表进行级联save
         *
         * @param saved
         */
        public void saveRelation(T saved) {
            if (!relatedEntity) {
                List<?> ids = null;
                try {
                    ids = (List<?>) getter.invoke(saved);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (CollectionUtils.isEmpty(ids)) {
                    return;
                }
                jdbcTemplate.batchUpdate(sqlAssociate, ids.stream().map(targetId -> new Object[]{saved.getId(), targetId}).collect(Collectors.toList()));
            } else {
                List<? extends BaseEntity<?>> toBeSavedList = null;
                try {
                    toBeSavedList = (List<? extends BaseEntity<?>>) getter.invoke(saved);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (CollectionUtils.isEmpty(toBeSavedList)) {
                    return;
                }
                jdbcTemplate.batchUpdate(sqlAssociate, toBeSavedList.stream().map(target -> new Object[]{saved.getId(), target.getId()}).collect(Collectors.toList()));
            }


        }
    }

    /**
     * 根据id获取详细信息。若需要join多表查询，则mapper中需定义名为getDetail的方法，否则只会调用get进行单表查询。
     *
     * @param id
     * @return
     */
    public T getDetail(K id) {
        T t = null;
        if (METHOD_GET_DETAIL == null) {
            t = get(id);
        } else {
            try {
                int count = METHOD_GET_DETAIL.getParameterCount();
                if (count == 1) {
                    t = (T) METHOD_GET_DETAIL.invoke(this.mapper, id);
                } else if (count == 2) {
                    t = (T) METHOD_GET_DETAIL.invoke(this.mapper, id, CurrentUser.getUserId());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (t != null) {
            postFindDetail(t);
        }

        return t;
    }

    protected void postFindDetail(T t) {
    }

    public final <A extends BaseEntity<?>> Set<Long> fetchedUserIds(A t) {
        Set<Long> set = new HashSet<>(fetchedDefaultUserIds(t));
        set.addAll(fetchedExtraUserIds(t));
        return set.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * 在getDetail返回实体类时，会默认获取createId、modifyId对应的用户名
     *
     * @param t
     * @return
     */
    public final <A extends BaseEntity<?>> Collection<Long> fetchedDefaultUserIds(A t) {
        List<Long> ids = new ArrayList<>();
        if (t instanceof AuditEntity<?>) {
            AuditEntity<?> ae = (AuditEntity<?>) t;
            ids.add(ae.getCreateId());
            ids.add(ae.getModifyId());
        }
        return ids;
    }

    /**
     * 在getDetail返回实体类时，会获取@NameGenerator（且没有通过value指定枚举值）所标注的字段对应的用户名
     *
     * @param t
     * @return
     */
    public final <A extends BaseEntity<?>> Collection<Long> fetchedExtraUserIds(A t) {
        Set<Long> set = new HashSet<>();
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            NameGenerator annotation = field.getAnnotation(NameGenerator.class);
            if (annotation != null && annotation.value().length == 0) {
                Object value = null;
                try {
                    value = field.get(t);
                } catch (IllegalAccessException e) {
                }
                if (value != null) {
                    set.add((Long) value);
                }

            }
        }
        return set;
    }

    protected static Map<Long, User> fetchUserFromRemote(Set<Long> ids) {
        try {
            Object uaaServerApi = context.getBean(Constants.SERVICE_UAA);
            Method internalFindByIds = uaaServerApi.getClass().getDeclaredMethod("internalFindByIds", Set.class);
            return (Map<Long, User>) internalFindByIds.invoke(uaaServerApi, ids);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateInternal(T t) {
        if (t instanceof AuditEntity<?>) {
            AuditEntity<?> ae = (AuditEntity<?>) t;
            ae.setModifyId(CurrentUser.getUserId());
            ae.setModifyTime(new Date());
        }
        mapper.updateByPrimaryKeySelective(t);
    }

    protected void checkExistence(K id) {
        if (!exists(id)) {
            throw new DataNotFoundException();
        }
    }

    public void update(K id, Object updateBo) {
        T stored = getDetail(id);
        if (stored == null) {
            throw new DataNotFoundException();
        }

        T toBeUpdated = null;
        try {
            toBeUpdated = ENTITY_CLASS.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        BeanUtils.copyProperties(updateBo, toBeUpdated);
        //BeanUtils.copyProperties会拷贝所有属性（包括值为null的属性），所以在拷贝之后（而不是之前）设置id
        toBeUpdated.setId(id);

        preUpdate(stored, toBeUpdated, updateBo.getClass());

        updateInternal(toBeUpdated);

        if (!SET_ONE_TO_MANY.isEmpty()) {
            for (OneToManyData oneToManyData : SET_ONE_TO_MANY) {
                if (BeanUtils.getPropertyDescriptor(updateBo.getClass(), oneToManyData.fieldName) != null) {
                    oneToManyData.updateRelation(toBeUpdated, id);
                }
            }
        }

        if (!SET_MANY_TO_MANY.isEmpty()) {
            for (ManyToManyData manyToManyData : SET_MANY_TO_MANY) {
                if (BeanUtils.getPropertyDescriptor(updateBo.getClass(), manyToManyData.fieldName) != null) {
                    manyToManyData.updateRelation(toBeUpdated, id);
                }
            }
        }

        postUpdate(stored, toBeUpdated, updateBo.getClass());
    }

    protected void preUpdate(T stored, T toBeUpdated, Class<?> updateBoClass) {
    }

    protected void postUpdate(T stored, T updated, Class<?> updateBoClass) {
    }

    public void save(T toBeSaved) {
        if (toBeSaved instanceof AuditEntity<?>) {
            AuditEntity<?> ae = (AuditEntity<?>) toBeSaved;
            ae.setCreateId(CurrentUser.getUserId());
            ae.setCreateTime(new Date());
        }
//        if (toBeSaved instanceof ClientAware) {
//            ClientAware ca = (ClientAware) toBeSaved;
//            ca.setFromApp();
//        }

        preSave(toBeSaved);

        mapper.insertSelective(toBeSaved);

        if (!SET_MANY_TO_MANY.isEmpty()) {
            for (ManyToManyData manyToManyData : SET_MANY_TO_MANY) {
                manyToManyData.saveRelation(toBeSaved);
            }
        }

        postSave(toBeSaved);
    }

    protected void preSave(T toBeSaved) {
    }

    protected void postSave(T saved) {
    }

    public List<T> findAll() {
        CustomQuery.Builder builder = CustomQuery.builder();
        customForFindAll(builder);
        return findAll(builder.build());
    }

    protected void customForFindAll(CustomQuery.Builder builder) {
    }

    public List<T> findAll(CustomQuery customQuery) {
        Example example = new Example(ENTITY_CLASS);
        Example.Criteria criteria = example.createCriteria();
        if (customQuery != null) {
            if (StringUtils.isNotEmpty(customQuery.getSelectProperties())) {
                example.selectProperties(customQuery.getSelectProperties().split(","));
            }
            if (!CollectionUtils.isEmpty(customQuery.getAndCondition())) {
                for (String andCondition : customQuery.getAndCondition()) {
                    if (StringUtils.isNotEmpty(andCondition)) {
                        criteria.andCondition(andCondition);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(customQuery.getPreparedAndCondition())) {
                for (Object[] arr : customQuery.getPreparedAndCondition()) {
                    String condition = (String) arr[0];
                    if (StringUtils.isNotEmpty(condition)) {
                        criteria.andCondition(condition, arr[1]);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(customQuery.getOrCondition())) {
                for (String orCondition : customQuery.getOrCondition()) {
                    if (StringUtils.isNotEmpty(orCondition)) {
                        criteria.orCondition(orCondition);
                    }
                }
            }
            if (StringUtils.isNotEmpty(customQuery.getOrderByClause())) {
                example.setOrderByClause(customQuery.getOrderByClause());
            }
        }
        return mapper.selectByExample(example);
    }

    public int count(CustomQuery customQuery) {
        Example example = new Example(ENTITY_CLASS);
        Example.Criteria criteria = example.createCriteria();
        example.setCountProperty("id");
        if (customQuery != null) {
            if (!CollectionUtils.isEmpty(customQuery.getAndCondition())) {
                for (String andCondition : customQuery.getAndCondition()) {
                    if (StringUtils.isNotEmpty(andCondition)) {
                        criteria.andCondition(andCondition);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(customQuery.getOrCondition())) {
                for (String orCondition : customQuery.getOrCondition()) {
                    if (StringUtils.isNotEmpty(orCondition)) {
                        criteria.orCondition(orCondition);
                    }
                }
            }
        }
        return mapper.selectCountByExample(example);
    }

    public boolean exists(K id) {
        CustomQuery.Builder builder = CustomQuery.builder().andCondition("id=" + id);
        customForExists(builder);
        int count = count(builder.build());
        return count > 0;
    }

    protected void customForExists(CustomQuery.Builder builder) {
    }

    public static void fetchUserNames(Set<Long> ids) {
        ids = ids.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        if (!ids.isEmpty()) {
            Map<Long, User> userMap = fetchUserFromRemote(ids);
            UserNameHolder.set(userMap);
        }
    }

    protected void customForFetchedUserIds(T t, Set<Long> ids) {

    }

    public final <A extends BaseEntity<?>> void fetchUserNames(A t) {
        Set<Long> ids = fetchedUserIds(t);
        fetchUserNames(ids);
    }

    public T get(CustomQuery customQuery) {
        List<T> list = findAll(customQuery);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 根据id获取详细信息。单表查询。可以通过重写customForGet方法指定查询细节。
     *
     * @param id
     * @return
     */
    public T get(K id) {
        CustomQuery.Builder builder = CustomQuery.builder().preparedAndCondition("id=", id);
        customForGet(builder);
        return get(builder.build());
    }

    protected void customForGet(CustomQuery.Builder builder) {

    }

    protected Set<Long> generateOwners(int ownerType) {
        Set<Long> owners = new HashSet<>();
        if (ownerType == 1) {
            owners.add(CurrentUser.getUserId());
        } else {
            List<User> users = uaaServerApi.allInferiors(CurrentUser.getUserId());
            owners.addAll(users.stream().map(User::getId).collect(Collectors.toSet()));
            if (ownerType == 0) {
                owners.add(CurrentUser.getUserId());
            }
        }
        return owners;
    }

}
