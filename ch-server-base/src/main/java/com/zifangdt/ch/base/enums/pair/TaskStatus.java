package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2017/10/25.
 */
public enum TaskStatus implements PairedEnum {
    PENDING(0, "未处理"), PROCESSED(1, "已处理");

    final int intVerifier;
    final String name;

    TaskStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

    public static enum  InventoryStatus implements PairedEnum {

        NOT_INVENTORY(1, "未盘点"), INVENTORY(2, "盘点中"), HAVE_INVENTORY(3, "已盘点"),WAIT_INVENTORY(4, "待盘点");

        final int intVerifier;
        final String name;

        InventoryStatus(int intVerifier, String name) {
            this.intVerifier = intVerifier;
            this.name = name;
        }

    }

    public static enum  IsNewStatus implements PairedEnum {

        IS_NEW(1, "新增"), IS_OLD(2, "原始");

        final int intVerifier;
        final String name;

        IsNewStatus(int intVerifier, String name) {
            this.intVerifier = intVerifier;
            this.name = name;
        }
    }

    public static enum  ProjectAccountStatus implements PairedEnum{

        NOT_PAY(1, "未结算"), PAYING(2, "结算中"), HAVE_PAY(3, "已结算");

        final int intVerifier;
        final String name;

        ProjectAccountStatus(int intVerifier, String name) {
            this.intVerifier = intVerifier;
            this.name = name;
        }

    }
}
