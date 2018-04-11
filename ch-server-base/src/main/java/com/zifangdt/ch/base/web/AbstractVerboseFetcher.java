package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/15.
 */
public abstract class AbstractVerboseFetcher<T, U extends BaseEntity<T>> {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final ThreadLocal<Map<T, U>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);
    private final Set<T> COLLECTED_IDS = new HashSet<>();
    private final Class<T> idClass;

    @SuppressWarnings("unchecked")
    AbstractVerboseFetcher() {
        this.idClass = (Class<T>) ClassUtil.getSuperclassActualTypes(getClass())[0];
    }

    void clearCollectedIds() {
        this.COLLECTED_IDS.clear();
    }

    @SuppressWarnings("unchecked")
    void addCollectedId(Class<?> fieldType, Object value) {
        if (fieldType == idClass) {
            COLLECTED_IDS.add((T) value);
        } else if (fieldType.isArray() && fieldType.getComponentType() == idClass && Array.getLength(value) > 0) {
            COLLECTED_IDS.addAll(Arrays.asList((T[]) value));
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            Collection<?> collection = (Collection<?>) value;
            if (!collection.isEmpty() && collection.iterator().next().getClass() == idClass) {
                COLLECTED_IDS.addAll((Collection<? extends T>) collection);
            }
        }
    }

    void collect() {
        //issue CH-582
//        COLLECTED_IDS.removeAll(THREAD_LOCAL.get().keySet());
        if (!COLLECTED_IDS.isEmpty()) {
//            LOGGER.debug(">>>ready to fetch info with ids:" + COLLECTED_IDS);
            fetch(COLLECTED_IDS);
        }
    }

    protected abstract List<U> doFetch(Collection<T> ids);

    public List<U> fetch(Collection<T> ids) {
        List<U> result = doFetch(ids);
        THREAD_LOCAL.get().putAll(result.stream().collect(Collectors.toMap(BaseEntity::getId, Function.identity())));
        return result;
    }

    public List<U> cachedList(Collection<T> ids) {
        return ids.stream().map(THREAD_LOCAL.get()::get).collect(Collectors.toList());
    }

    public U one(T id) {
        U cached = cachedOne(id);
        if (cached != null) {
            return cached;
        }
        List<U> fetched = fetch(Collections.singletonList(id));
        return fetched.isEmpty() ? null : fetched.get(0);
    }

    public U cachedOne(T id) {
        return THREAD_LOCAL.get().get(id);
    }

    public Class<T> getIdClass() {
        return idClass;
    }

    public abstract String nameOf(U verbose);

    public List<?> custom(Collection<T> ids) {
        return new ArrayList<>(ids);
    }
}
