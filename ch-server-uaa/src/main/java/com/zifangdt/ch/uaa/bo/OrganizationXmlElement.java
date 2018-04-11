package com.zifangdt.ch.uaa.bo;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁兵 on 2017/9/22.
 */
@XmlRootElement(name = "organ")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationXmlElement {
    @XmlAttribute
    private Long id;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private Long parentId;
    @XmlElement(name = "organ")
    private List<OrganizationXmlElement> children = new ArrayList<>();
    @XmlElement(name = "user")
    private List<UserXmlElement> users;

    public OrganizationXmlElement() {
    }

    public OrganizationXmlElement(Long id, String name, Long parentId, List<UserXmlElement> users) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.users = users;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrganizationXmlElement> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationXmlElement> children) {
        this.children = children;
    }

    public List<UserXmlElement> getUsers() {
        return users;
    }

    public void setUsers(List<UserXmlElement> users) {
        this.users = users;
    }
}
