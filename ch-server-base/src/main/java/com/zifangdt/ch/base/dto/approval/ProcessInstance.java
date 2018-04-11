package com.zifangdt.ch.base.dto.approval;

import com.zifangdt.ch.base.bo.InstantStep;
import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.pair.ProcessStatus;
import com.zifangdt.ch.base.enums.pair.ProcessType;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by 袁兵 on 2017/10/26.
 */
@Table(name = "biz_process_instance")
public class ProcessInstance extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = -5159232841377706650L;

    private String name;
    @NamedProperty
    private ProcessType type;
    private Long object;
    private Date startTime;
    private Date endTime;
    @NamedProperty
    private ProcessStatus status;

    private Set<Long> currentApprovers;
    private Set<Long> involvedApprovers;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long initiator;
    private Long currentTrack;
    private Date updateTime;

    @Transient
    private List<RunningTrack> tracks;
    @Transient
    private List<InstantStep> steps;
    @Transient
    private Object relatedObject;
    @Transient
    private ProcessOverview processOverview;

    public ProcessOverview getProcessOverview() {
        return processOverview;
    }

    public void setProcessOverview(ProcessOverview processOverview) {
        this.processOverview = processOverview;
    }

    public List<RunningTrack> getTracks() {
        return tracks;
    }

    public void setTracks(List<RunningTrack> tracks) {
        this.tracks = tracks;
    }

    public Object getRelatedObject() {
        return relatedObject;
    }

    public void setRelatedObject(Object relatedObject) {
        this.relatedObject = relatedObject;
    }

    public List<InstantStep> getSteps() {
        return steps;
    }

    public void setSteps(List<InstantStep> steps) {
        this.steps = steps;
    }

    public ProcessInstance() {

    }

    public ProcessInstance(String name, ProcessType type, Long object) {
        this.name = name;
        this.type = type;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProcessType getType() {
        return type;
    }

    public void setType(ProcessType type) {
        this.type = type;
    }

    public Long getObject() {
        return object;
    }

    public void setObject(Long object) {
        this.object = object;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public Set<Long> getCurrentApprovers() {
        return currentApprovers;
    }

    public void setCurrentApprovers(Set<Long> currentApprovers) {
        this.currentApprovers = currentApprovers;
    }

    public Set<Long> getInvolvedApprovers() {
        return involvedApprovers;
    }

    public void setInvolvedApprovers(Set<Long> involvedApprovers) {
        this.involvedApprovers = involvedApprovers;
    }

    public Long getInitiator() {
        return initiator;
    }

    public void setInitiator(Long initiator) {
        this.initiator = initiator;
    }

    public Long getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Long currentTrack) {
        this.currentTrack = currentTrack;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
