package com.zifangdt.ch.uaa.mapper;

import com.zifangdt.ch.base.dto.uaa.Permission;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.uaa.bo.UserWebQueryBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 袁兵 on 2017/8/24.
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(String username);

    User findByPhone(String phone);

    List<Permission> findPermissionsByUserId(Long id);

    boolean isHead(Long id);

    boolean usernameExists(String username);

    boolean phoneExists(String phone);

    User getDetail(Long id);

    void associatePermissionsByOrganId(@Param("userId") Long userId, @Param("organId") Long organId);

    List<User> findAll();

    List<User> findByIds(@Param("ids") Set<Long> ids);

    default List<User> internalFindByIds(Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return findByIds(ids);
    }

    User get(Long id);

    void unAssociatePermissions(@Param("userIds") List<Long> userIds);

    void associatePermissions(@Param("userIds") List<Long> userIds, @Param("permissionIds") List<Long> permissionIds);

    List<User> findListForWeb(UserWebQueryBo userWebQueryBo);

    String queryChildOrganIds(Long organId);

    String queryInferiorIds(@Param("userId") Long userId, @Param("underDirectly") boolean underDirectly);

    String querySuperiorIds(Long userId);

    List<Long> findIdsByOrganId(Long organId);

    List<Long> findIdsByOrganIds(@Param("organIds") List<Long> organIds);

    List<User> findAllWithDeleted();

    void updateApproverOf(@Param("inserts") Set<Long> inserts, @Param("deletes") Set<Long> deletes, @Param("bitMask") int bitMask);
}
