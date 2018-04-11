package com.zifangdt.ch.base.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 袁兵 on 2017/9/14.
 */
public class CustomQuery {
    private String orderByClause;
    private String selectProperties;
    private List<String> andCondition;
    private List<String> orCondition;

    private List<Object[]> preparedAndCondition;

    public List<Object[]> getPreparedAndCondition() {
        return preparedAndCondition;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public String getSelectProperties() {
        return selectProperties;
    }

    public List<String> getAndCondition() {
        return andCondition;
    }

    public List<String> getOrCondition() {
        return orCondition;
    }

    private CustomQuery(String orderByClause, String selectProperties, List<String> andCondition, List<String> orCondition, List<Object[]> preparedAndCondition) {
        this.orderByClause = orderByClause;
        this.selectProperties = selectProperties;
        this.andCondition = andCondition;
        this.orCondition = orCondition;
        this.preparedAndCondition = preparedAndCondition;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String orderByClause;
        private String selectProperties;
        private List<String> andCondition = new ArrayList<>();
        private List<String> orCondition = new ArrayList<>();
        private List<Object[]> preparedAndCondition = new ArrayList<>();

        public Builder orderByClause(String orderByClause) {
            this.orderByClause = orderByClause;
            return this;
        }

        public Builder selectProperties(String selectProperties) {
            this.selectProperties = selectProperties;
            return this;
        }

        public Builder andCondition(String andCondition) {
            this.andCondition.add(andCondition);
            return this;
        }

        public Builder preparedAndCondition(String condition, Object value) {
            this.preparedAndCondition.add(new Object[]{condition, value});
            return this;
        }

        public Builder orCondition(String orCondition) {
            this.orCondition.add(orCondition);
            return this;
        }

        public CustomQuery build() {
            return new CustomQuery(orderByClause, selectProperties, andCondition, orCondition, preparedAndCondition);
        }
    }
}
