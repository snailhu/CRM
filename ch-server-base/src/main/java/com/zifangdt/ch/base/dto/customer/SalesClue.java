package com.zifangdt.ch.base.dto.customer;

import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.SalesClueStatus;
import com.zifangdt.ch.base.validation.MobileNumber;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by 袁兵 on 2017/12/5.
 */
@Table(name = "biz_sales_clue")
public class SalesClue extends AuditEntity<Long> {
    @NotBlank
    private String name;
    private String source;
    @NotNull
    private Long organId;
    @NotBlank
    private String contactName;
    @NotBlank
    @MobileNumber
    private String contactPhone;
    private String contactAddress;
    private String contactHouseNumber;
    private String contactRemark;
    private String contactTag;
    @NamedProperty
    private SalesClueStatus status;

    @Transient
    private List<SalesClueNews> news;
    @Transient
    private String organName;
    @Transient
    private List<SalesClueFollowUp> followUps;

    public List<SalesClueFollowUp> getFollowUps() {
        return followUps;
    }

    public void setFollowUps(List<SalesClueFollowUp> followUps) {
        this.followUps = followUps;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public List<SalesClueNews> getNews() {
        return news;
    }

    public void setNews(List<SalesClueNews> news) {
        this.news = news;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactHouseNumber() {
        return contactHouseNumber;
    }

    public void setContactHouseNumber(String contactHouseNumber) {
        this.contactHouseNumber = contactHouseNumber;
    }

    public String getContactRemark() {
        return contactRemark;
    }

    public void setContactRemark(String contactRemark) {
        this.contactRemark = contactRemark;
    }

    public String getContactTag() {
        return contactTag;
    }

    public void setContactTag(String contactTag) {
        this.contactTag = contactTag;
    }

    public SalesClueStatus getStatus() {
        return status;
    }

    public void setStatus(SalesClueStatus status) {
        this.status = status;
    }
}
