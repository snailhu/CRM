package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.bo.cfg.base.DetailOfFlowStep;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/20.
 */
public class InstantStep {
    private boolean current;
    private String name;

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InstantStep() {
    }

    public InstantStep(boolean current, String name) {
        this.current = current;
        this.name = name;
    }

    public static List<InstantStep> from(List<DetailOfFlowStep> steps, String currentUuid) {
        return steps.stream().map(s -> new InstantStep(s.getUuid().equals(currentUuid), s.getName())).collect(Collectors.toList());
    }
}
