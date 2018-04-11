package com.zifangdt.ch.base.enums;

public enum ProductNewsType implements NewsType {
    CREATE("#operator#创建了产品#product#"),
    UPDATE("#operator#更新了产品#product#");

    private final String contentTemplate;

    ProductNewsType(String contentTemplate){
        this.contentTemplate = contentTemplate;
    }

    @Override
    public String getContentTemplate() {
        return contentTemplate;
    }
}
