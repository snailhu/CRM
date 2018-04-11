package com.zifangdt.ch.base.dto.customer;

import com.zifangdt.ch.base.dto.AuditEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by 袁兵 on 2018/2/3.
 */
@Table(name = "cfg_residential_area")
public class ResidentialArea extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = -7879120675354316615L;
    @NotBlank
    private String name;
    private String alias;
    @NotBlank
    private String provinceId;
    private String provinceName;
    @NotBlank
    private String cityId;
    private String cityName;
    @NotBlank
    private String regionId;
    private String regionName;
    @NotBlank
    private String detailedAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }
}
