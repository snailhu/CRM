package com.zifangdt.ch.base.dto.todo;

import java.util.Date;

/**
 * Created by 袁兵 on 2017/11/10.
 */
public class ScheduleDeleteOnceRecord {
    private Long scheduleId;
    private Date date;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
