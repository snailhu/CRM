package com.zifangdt.ch.base.enums;

import com.zifangdt.ch.base.dto.common.DataDictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2017/9/8.
 */
public enum DataEnum {

    customerPotentialStages(1001),
    customerTypes(1002),
    customerSources(1003),
    customerStages(1004),
    customerWorth(1005),
    customerOrderSizes(1006),
    customerImportance(1007),
    customerActionTypes(1008),
    salesClueStatus(1009),
    salesClueFollowUpStatus(1010),

    userLevels(2001),
    userLoginLimits(2002),

    contractTypes(4001),
    contractPayStyles(4002),
    contractReturnTypes(4003),
    contractReturnPayStyles(4004),
    contractAccountingTypes(4005),

    taskUrgency(5001),
    taskTypes(5002),
    taskRepeatStyles(5003),

    approvalTypes(6001),
    approvalStatus(6002),

    systemLogEvents(7001),

    invoiceTypes(8001)
    ;


    private int type;
    private Map<Long, String> pairs = new LinkedHashMap<>();
    private static Map<DataEnum, Set<Long>> exclusions = new HashMap<>();
    private boolean init = false;

    DataEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void addOrReplacePair(Long key, String value) {
        pairs.put(key, value);
    }

    public void removePair(Long key) {
        pairs.remove(key);
    }

    public void initPairs(List<DataDictionary> list) {
        if (!init) {
            for (DataDictionary dataDictionary : list) {
                this.pairs.put(dataDictionary.getId(), dataDictionary.getValue());

                if (!StringUtils.isEmpty(dataDictionary.getExclusions())) {
                    List<Long> ids = Arrays.stream(dataDictionary.getExclusions().split(",")).map(Long::valueOf).collect(Collectors.toList());
                    if (!exclusions.containsKey(this)) {
                        exclusions.put(this, new HashSet<>());
                    }
                    exclusions.get(this).addAll(ids);
                }
            }
            init = true;
        }
    }

    public Set<Long> getExclusions() {
        if (!init) {
            throw new IllegalStateException(name() + "未初始化");
        }
        return exclusions.get(this);
    }

    public List<String> getValues() {
        if (!init) {
            throw new IllegalStateException(name() + "未初始化");
        }
        if (CollectionUtils.isEmpty(pairs)) {
            return null;
        } else {
            return new ArrayList<>(pairs.values());
        }
    }

    public List<Long> getKeys() {
        if (!init) {
            throw new IllegalStateException(name() + "未初始化");
        }
        if (CollectionUtils.isEmpty(pairs)) {
            return null;
        } else {
            return new ArrayList<>(pairs.keySet());
        }
    }

    public Long getDefaultKey() {
        List<Long> values = getKeys();
        if (CollectionUtils.isEmpty(values)) {
            return null;
        } else {
            return values.get(0);
        }
    }

    public static String getValueFromMultiInstances(Long key, DataEnum[] dataEnums) {
        for (DataEnum dataEnum : dataEnums) {
            String value = dataEnum.getValue(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public String getValue(Long key) {
        if (!init) {
            throw new IllegalStateException(name() + "未初始化");
        }
        return pairs.get(key);
    }

    public Long getKey(String value) {
        if (!init) {
            throw new IllegalStateException(name() + "未初始化");
        }
        for (Map.Entry<Long, String> entry : pairs.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static DataEnum getByType(int type) {
        for (DataEnum dataEnum : DataEnum.values()) {
            if (dataEnum.getType() == type) {
                return dataEnum;
            }
        }
        return null;
    }

    public boolean isCustomerData() {
        return type < 2000 && type > 1000;
    }

    public boolean isProductData() {
        return type > 3000 && type < 4000;
    }

    public boolean isContractData() {
        return type < 5000 && type > 4000;
    }

    public boolean isUaaData() {
        return type < 3000 && type > 2000;
    }

    public boolean isTaskData() {
        return type < 6000 && type > 5000;
    }

    public boolean isApprovalData() {
        return type < 7000 && type > 6000;
    }

}
