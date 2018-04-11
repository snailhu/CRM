package com.zifangdt.ch.base.enums.ticket;

import com.zifangdt.ch.base.exception.DataInvalidException;

import java.util.EnumSet;
import java.util.stream.Collectors;

public enum TicketStatus implements DisplayableEnum {
    WAIT_ASSIGN("待指派", EnumSet.of(
            TicketActionType.ASSIGN,
            TicketActionType.EDIT,
            TicketActionType.DELETE)),
    ASSIGNED("已指派", EnumSet.of(
            TicketActionType.ACCEPT,
            TicketActionType.REJECT,
            TicketActionType.DELETE)),
    ACCEPTED("已接受", EnumSet.of(
            TicketActionType.START,
            TicketActionType.DELETE)),
    ON_GOING("进行中", EnumSet.of(
            TicketActionType.CREATE_RECEIPT,
            TicketActionType.DELETE)),
    WAIT_FINISH("待完结", EnumSet.of(
            TicketActionType.FINISH,
            TicketActionType.REJECT_RECEIPT,
            TicketActionType.CONFIRM_RECEIPT,
            TicketActionType.CREATE_RETURN_VISIT,
            TicketActionType.CREATE_CLEARING,
            TicketActionType.DELETE)),
    FINISH("已完结", EnumSet.noneOf(TicketActionType.class)),
    DELETED("已删除", EnumSet.noneOf(TicketActionType.class));

    private final String name;
    private final EnumSet<TicketActionType> actions;

    TicketStatus(String name, EnumSet<TicketActionType> actions) {
        this.name = name;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public EnumSet<TicketActionType> getActions() {
        return actions;
    }

    public void valid(TicketActionType action) {
//        List<TicketActionType> actionTypes = TicketStatus.getTypes(this);
        EnumSet<TicketActionType> actionTypes = getActions();
        for (TicketActionType type: actionTypes) {
            if (type == null) throw new DataInvalidException("java问题，type=" + type);
        }
        if (!actionTypes.contains(action)) {
            String message = "在" + getName() + "状态下" + "禁止" + action.getName();
            throw new DataInvalidException(message);
        }
    }

    static public void main(String[] args) {
        EnumSet<TicketActionType> types = TicketStatus.WAIT_ASSIGN.getActions();
        int a = 1;
        int b = 2;
        TicketStatus.WAIT_ASSIGN.valid(TicketActionType.ASSIGN);
        int c = 3;
    }
}
