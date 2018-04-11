package com.zifangdt.ch.base.enums.pair;

import com.zifangdt.ch.base.enums.project.RefundNoticeStep;

/**
 * Created by 袁兵 on 2018/1/19.
 */
public enum ProcedureNode implements PairedEnum {

    APPROVING(1, "安装方案审批时"), DELIVERING(2, "发货时"), ASSIGNING(3, "指派时");

    final int intVerifier;
    final String name;

    ProcedureNode(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

    public static ProcedureNode from(RefundNoticeStep step) {
        switch (step) {
            case SchemeCheckOver:
                return APPROVING;
            case UnitAssign:
                return ASSIGNING;
            case Delivery:
                return DELIVERING;
        }
        return null;
    }
}
