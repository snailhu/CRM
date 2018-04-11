package com.zifangdt.ch.base.dto;

import com.zifangdt.ch.base.enums.StepStatus;

import java.util.Date;

/**
 * Created by 袁兵 on 2017/10/27.
 */
public interface RunningTrack {

    String getStep();
    StepStatus getStatus();
    Long getOperator();
    Date getCreateTime();
    Date getUpdateTime();

}
