package com.zifangdt.ch.uaa.bo;

import com.zifangdt.ch.base.constant.Constants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 袁兵 on 2017/9/22.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class UserXmlElement {

    private Long id;
    private String name;
    private String username;
    private Integer isHead;
    private Integer isBoss;
    private Long organizationId;
    private String organizationName;

    @XmlAttribute
    private Boolean getIsSuperior() {
        return isBoss == Constants.YES || isHead == Constants.YES;
    }

    public UserXmlElement() {
    }

    public UserXmlElement(Long id, String name, String username, Integer isHead, Integer isBoss, Long organizationId, String organizationName) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.isHead = isHead;
        this.isBoss = isBoss;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

    @XmlAttribute
    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @XmlAttribute
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlAttribute
    public Integer getIsHead() {
        return isHead;
    }

    public void setIsHead(Integer isHead) {
        this.isHead = isHead;
    }

    @XmlAttribute
    public Integer getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(Integer isBoss) {
        this.isBoss = isBoss;
    }
}
