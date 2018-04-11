package com.zifangdt.ch.base.enums.pair;

public enum ProductUnit implements PairedEnum{
    U1(1, "支");

    final int intVerifier;
    final String name;

    ProductUnit(int intVerifier, String name) {
        this.intVerifier = intVerifier;
        this.name = name;
    }
}
