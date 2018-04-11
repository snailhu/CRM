package com.zifangdt.ch.base.enums.pair;

/**
 * Created by 袁兵 on 2018/1/24.
 */
public enum Bank implements PairedEnum {
    ZS(1, "招商银行"),
    GS(2, "工商银行"),
    NY(3, "农业银行"),
    ZG(4, "中国银行"),
    JT(5, "交通银行"),
    ZX(6, "中信银行"),
    GD(7, "光大银行"),
    GF(8, "广发银行"),
    XY(9, "兴业银行"),
    MS(10, "民生银行"),
    CX(11, "储蓄银行"),
    PF(12, "浦发银行"),
    PA(13, "平安银行"),
    HX(14, "华夏银行"),
    BJ(15, "北京银行"),
    SH(16, "上海银行"),
    HQ(17, "花旗银行"),
    OTHER(18, "其他"),;

    final int intVerifier;
    final String name;

    Bank(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }

}
