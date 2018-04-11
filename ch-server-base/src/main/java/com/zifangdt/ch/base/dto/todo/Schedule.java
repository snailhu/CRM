package com.zifangdt.ch.base.dto.todo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zifangdt.ch.base.converter.JsonReplacer;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.enums.pair.CreatedFrom;
import com.zifangdt.ch.base.enums.pair.TaskRepeatStyle;
import com.zifangdt.ch.base.util.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.CollectionUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2017/11/6.
 */
@Table(name = "biz_schedule")
public class Schedule extends AuditEntity<Long> implements Serializable {
    private static final long serialVersionUID = -8599698034172275044L;

    @NotBlank
    private String name;
    @NotNull
    private Date reminderTime;
    private String remark;
    @NotNull
    @NamedProperty
    private TaskRepeatStyle repeatStyle;
    private Boolean deleted;

    @Transient
    @JsonIgnore
    private List<ScheduleDeleteOnceRecord> deleteOnceTimes;

    private CreatedFrom createdFrom;
    private Long createdFromId;

    public List<ScheduleDeleteOnceRecord> getDeleteOnceTimes() {
        return deleteOnceTimes;
    }

    public void setDeleteOnceTimes(List<ScheduleDeleteOnceRecord> deleteOnceTimes) {
        this.deleteOnceTimes = deleteOnceTimes;
    }

    public CreatedFrom getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(CreatedFrom createdFrom) {
        this.createdFrom = createdFrom;
    }

    public Long getCreatedFromId() {
        return createdFromId;
    }

    public void setCreatedFromId(Long createdFromId) {
        this.createdFromId = createdFromId;
    }

    @JsonReplacer("reminderTime")
    public String getReminderTimeString() {
        return reminderTime == null ? null : DateUtil.format(reminderTime, "HH:mm");
    }

    public boolean deletedOnceOn(Date date) {
        if (CollectionUtils.isEmpty(deleteOnceTimes) || repeatStyle==TaskRepeatStyle.NO) {
            return false;
        }
        return deleteOnceTimes.stream().anyMatch(r -> DateUtils.isSameDay(r.getDate(), date));
//        if (isRepeatStyleOf(TaskRepeatStyle.EVERY_DAY)) {
//            return deleteOnceTimes.stream().anyMatch(r -> DateUtils.isSameDay(r.getDate(), date));
//        } else if (isRepeatStyleOf(TaskRepeatStyle.EVERY_WEEK)) {
//            return deleteOnceTimes.stream().anyMatch(r -> DateUtil.getDay(r.getDate()) == DateUtil.getDay(date));
//        } else if (isRepeatStyleOf(TaskRepeatStyle.EVERY_MONTH)) {
//            return deleteOnceTimes.stream().anyMatch(r -> DateUtil.getDayOfMonth(r.getDate()) == DateUtil.getDayOfMonth(date));
//        } else {
//            return false;
//        }
    }

    public boolean deletedOnceOnDate(Date date) {
        return deleteOnceTimes.stream().anyMatch(r -> DateUtils.isSameDay(r.getDate(), date));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public TaskRepeatStyle getRepeatStyle() {
        return repeatStyle;
    }

    public void setRepeatStyle(TaskRepeatStyle repeatStyle) {
        this.repeatStyle = repeatStyle;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
