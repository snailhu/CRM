package com.zifangdt.ch.base.dto.finance;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.converter.VerboseProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.InvoiceType;
import com.zifangdt.ch.base.validation.PositiveNumber;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/9.
 */
@Table(name = "biz_invoice")
public class Invoice extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = -7916287288584553970L;

    @NotNull
    @NamedProperty
    private InvoiceType type;
    @NotNull
    private Date billingTime;
    @NotNull
    @PositiveNumber
    private BigDecimal billingMoney;
    @NotBlank
    private String number;
    @VerboseProperty(target = JsonPropertyTarget.FILE)
    private List<String> photos;
    private Long journalId;

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public Date getBillingTime() {
        return billingTime;
    }

    public void setBillingTime(Date billingTime) {
        this.billingTime = billingTime;
    }

    public BigDecimal getBillingMoney() {
        return billingMoney;
    }

    public void setBillingMoney(BigDecimal billingMoney) {
        this.billingMoney = billingMoney;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
