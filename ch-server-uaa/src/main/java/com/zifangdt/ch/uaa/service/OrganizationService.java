package com.zifangdt.ch.uaa.service;

import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.service.CustomQuery;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.base.util.HierarchyUtil;
import com.zifangdt.ch.uaa.bo.OrganizationXmlElement;
import com.zifangdt.ch.uaa.bo.UserXmlElement;
import com.zifangdt.ch.uaa.mapper.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2017/8/28.
 */
@Service
public class OrganizationService extends BaseService<Organization, Long> {
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private ApplicationEventPublisher publisher;

    public List<Organization> findTreeWithUser() {
        List<Organization> organs = organizationMapper.findAllWithUser();
        for (Organization organization : organs) {
            organization.setChildren(new ArrayList<>());
        }

        return HierarchyUtil.buildTree(organs);
    }

    @Override
    protected void preSave(Organization organization) {
        if (organizationMapper.nameExists(organization.getName())) {
            throw new DataInvalidException("该部门名称已被使用");
        }
        if (!exists(organization.getParentId())) {
            throw new DataNotFoundException();
        }
    }

    public void updateHead(Long id, Long headId) {
        checkExistence(id);
        Organization organization = new Organization();
        organization.setId(id);
        organization.setHeadId(headId);
        updateInternal(organization);

    }

    public void deleteHead(Long id) {
        Organization o = get(id);
        if (o == null) {
            throw new DataNotFoundException();
        }
        if (o.getHeadId() != null) {
            organizationMapper.deleteHead(id, CurrentUser.getUserId());
        }
    }

    public void deleteHead(Long id, Long userId) {
        Organization o = get(id);
        if (o == null) {
            throw new DataNotFoundException();
        }
        if (o.getHeadId() != null && o.getHeadId().equals(userId)) {
            organizationMapper.deleteHead(id, CurrentUser.getUserId());
        }
    }

    public void delete(Long id) {
        checkExistence(id);
        List<Long> organIds = organizationMapper.findOneLevelChildrenAndSelf(id).stream().map(Organization::getId).collect(Collectors.toList());
        if (organizationMapper.countUserMulti(organIds) > 0) {
            throw new WrongOperationException("需要先转移该部门及其子部门下所有用户才可删除");
        }
        organizationMapper.deleteMulti(organIds, CurrentUser.getUserId());
    }

    @Override
    protected void customForExists(CustomQuery.Builder builder) {
        builder.andCondition("deleted=0");
    }

    public void transfer(Long id, Long newParentId) {
        Organization o = organizationMapper.selectByPrimaryKey(id);
        if (o == null) {
            throw new DataNotFoundException();
        }
        if (o.getId().equals(newParentId)) {
            throw new WrongOperationException("不能指定自己作为父部门");
        }
        if (!o.getParentId().equals(newParentId)) {
            if (!exists(newParentId)) {
                throw new DataNotFoundException();
            }
            Organization organization = new Organization();
            organization.setId(id);
            organization.setParentId(newParentId);
            updateInternal(organization);

        }
    }

    @Cacheable(value = "organization", cacheManager = "requestCacheManager")
    @Transactional(readOnly = true)
    public Long getIdByName(String name) {
        return organizationMapper.getIdByName(name);
    }

    @Override
    protected void preUpdate(Organization stored, Organization toBeUpdated, Class<?> updateBoClass) {
        if (!stored.getName().equals(toBeUpdated.getName()) &&
                organizationMapper.nameExists(toBeUpdated.getName())) {
            throw new DataInvalidException("该部门名称已被使用");
        }
    }

    public OrganizationXmlElement findRootXmlElement() {
        List<Organization> organs = organizationMapper.findAllWithUser();
        List<OrganizationXmlElement> list = organs.stream().map(organ -> {
            return new OrganizationXmlElement(
                    organ.getId(),
                    organ.getName(),
                    organ.getParentId(),
                    organ.getUsers().stream().map(user -> {
                        return new UserXmlElement(
                                user.getId(),
                                user.getName(),
                                user.getUsername(),
                                user.getIsHead(),
                                user.getIsBoss(),
                                organ.getId(),
                                organ.getName()
                        );
                    }).collect(Collectors.toList())
            );
        }).collect(Collectors.toList());

        List<OrganizationXmlElement> result = new ArrayList<>();
        for (OrganizationXmlElement o1 : list) {
            boolean mark = false;
            for (OrganizationXmlElement o2 : list) {
                if (o1.getParentId() != null && o1.getParentId().equals(o2.getId())) {
                    mark = true;
                    o2.getChildren().add(o1);
                    break;
                }
            }
            if (!mark) {
                result.add(o1);
            }
        }
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * 获取部门和部门下所有的用户
     *
     * @param id
     * @return
     */
    public List<Long> getWithUser(Long id) {
        return organizationMapper.findUserIds(id);
    }

    public List<Organization> findOneLevelChildrenAndSelf(Long id) {
        return organizationMapper.findOneLevelChildrenAndSelf(id);
    }

    public List<Organization> findOneLevelChildren(Long id) {
        return organizationMapper.findOneLevelChildren(id);
    }

    public List<Organization> findTreeWithMixed() {
        List<Organization> organs = organizationMapper.findAllWithUser();

        Map<Long, Organization> mapForOrgan = new HashMap<>();
        for (Organization o : organs) {
            o.setMixed(new ArrayList<>());
            o.getMixed().addAll(o.getUsers());
            o.setUsers(null);
            mapForOrgan.put(o.getId(), o);
        }
        Iterator<Organization> it = organs.iterator();
        while (it.hasNext()) {
            Organization o = it.next();
            if (o.getParentId() != null) {
                Organization oo = mapForOrgan.get(o.getParentId());
                if (oo != null) {
                    oo.getMixed().add(o);
                } else {
                    //说明父部门被删除
                }
                it.remove();
            }
        }
        return organs;
    }

    public List<Organization> findAll(){
        return organizationMapper.findAll();
    }
}
