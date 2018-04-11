package com.zifangdt.ch.base.enums;

import com.zifangdt.ch.base.enums.pair.IntVerifierEnum;

/**
 * Created by 袁兵 on 2018/1/12.
 */
public enum Terminal implements IntVerifierEnum {
    WEB(1),
    APP(2),
    BOTH(3);

    final int intVerifier;

    Terminal(int intVerifier) {
        this.intVerifier = intVerifier;
    }

}
