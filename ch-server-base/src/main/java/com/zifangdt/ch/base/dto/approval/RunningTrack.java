package com.zifangdt.ch.base.dto.approval;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.pair.StepStatus;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Table(name = "biz_running_track")
public class RunningTrack extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = -8536965148987019635L;

    private Long processInstanceId;
    private String name;
    private String uuid;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long committer;
    @NamedProperty
    private StepStatus status;
    private Date createTime;
    private Date commitTime;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RunningTrack() {
    }

    public RunningTrack(Long processInstanceId, String name, String uuid) {
        this.processInstanceId = processInstanceId;
        this.name = name;
        this.uuid = uuid;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getCommitter() {
        return committer;
    }

    public void setCommitter(Long committer) {
        this.committer = committer;
    }

    public StepStatus getStatus() {
        return status;
    }

    public void setStatus(StepStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }
}
