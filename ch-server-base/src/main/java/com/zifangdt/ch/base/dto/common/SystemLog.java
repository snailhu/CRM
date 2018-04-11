package com.zifangdt.ch.base.dto.common;

import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.pair.SystemLogEvent;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 袁兵 on 2017/10/31.
 */
@Table(name = "sys_log")
public class SystemLog extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = 2128720323635740375L;

    private String operatorName;
    private String operatorUsername;
    private String operatorPhone;
    private boolean fromApp;
    private String deviceName;
    @NotNull
    @NamedProperty
    private SystemLogEvent eventType;
    private String deviceId;
    private String ip;
    private String version;
    private Date createTime;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorUsername() {
        return operatorUsername;
    }

    public void setOperatorUsername(String operatorUsername) {
        this.operatorUsername = operatorUsername;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public boolean getFromApp() {
        return fromApp;
    }

    public void setFromApp(boolean fromApp) {
        this.fromApp = fromApp;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public SystemLogEvent getEventType() {
        return eventType;
    }

    public void setEventType(SystemLogEvent eventType) {
        this.eventType = eventType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
