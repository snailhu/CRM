package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/4/10.
 */
public enum JournalBulkRecordStatus implements PairedEnum {
    PENDING(1, "待处理"),
    DONE(2, "已处理"),
    DELETED(3, "已删除");

    final int intVerifier;
    final String name;

    JournalBulkRecordStatus(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
