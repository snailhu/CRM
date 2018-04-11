package com.zifangdt.ch.base.dto.uaa;

import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dao.ManyToManyRelation;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.LoginLimit;
import com.zifangdt.ch.base.enums.pair.UserLevel;
import com.zifangdt.ch.base.validation.IntegerEnumValue;
import com.zifangdt.ch.base.validation.MobileNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "sys_user")
public class User extends AuditEntity<Long> implements Serializable {

    private static final long serialVersionUID = -6907385484004870621L;

    /**
     * 姓名
     */
    @NotBlank
    private String name;
    /**
     * 用户名
     */
    @NotBlank
    private String username;
    /**
     * 手机号码
     */
    @MobileNumber
    @NotBlank
    private String phone;
    /**
     * 性别，0男，1女
     */
    @IntegerEnumValue({0, 1})
    private Integer gender;
    /**
     * 电子邮箱
     */
    @Email
    private String email;
    @NotBlank
    private String password;
    private Date birthday;
    private Long organizationId;
    private Integer isBoss;
    private String description;
    private Integer status;
    private Integer deleted;
    private String tag;

    private String avatar;

    private LoginLimit loginLimit;

    @Transient
    private String loginLimitName;

    private Integer approverOf;

    public Integer getApproverOf() {
        return approverOf;
    }

    public void setApproverOf(Integer approverOf) {
        this.approverOf = approverOf;
    }

    public String getLoginLimitName() {
        return loginLimitName;
    }

    public void setLoginLimitName(String loginLimitName) {
        this.loginLimitName = loginLimitName;
    }

    @Transient
    private Integer isHead;
    @Transient
    private String organizationName;
    @Transient
    @ManyToManyRelation(value = "sys_user_permission", targetKeyColumn = "permission_id")
    private List<Long> permissions;

    @Transient
    private List<Permission> permissionList;

    public List<Long> getPermissions() {
        return permissions;
    }

    @Transient
    private String levelName;

    @Transient
    private UserLevel level;

    public void setPermissions(List<Long> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public LoginLimit getLoginLimit() {
        return loginLimit;
    }

    public void setLoginLimit(LoginLimit loginLimit) {
        this.loginLimit = loginLimit;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(Integer isBoss) {
        this.isBoss = isBoss;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getIsHead() {
        return isHead;
    }

    public void setIsHead(Integer isHead) {
        this.isHead = isHead;
    }

    @Transient
    private String displayInfo;

    public String getDisplayInfo() {
        return displayInfo;
    }

    public String displayInfo() {
        StringBuilder sb = new StringBuilder();
        if (getIsBoss() == Constants.YES) {
            sb.append("Boss-" + getName());
        } else {
            sb.append(getOrganizationName());
            if (getIsHead() == Constants.YES) {
                sb.append("主管");
            }
            sb.append("-" + getName());
        }
        displayInfo = sb.toString();
        return displayInfo;
    }

    public static User DELETED_NONEXIST_USER = new User();

    static {
        DELETED_NONEXIST_USER.setId(0l);
        DELETED_NONEXIST_USER.setName("该用户已经被删除或不存在");
        DELETED_NONEXIST_USER.setUsername("该用户已经被删除或不存在");
    }
}
