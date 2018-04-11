package com.zifangdt.ch.base.enums.setting;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 快捷入口枚举
 */
@JsonSerialize(using = QuickEntryEnumSerializer.class)
public enum QuickEntryEnum {
    Daily(1l, "日报", ""),
    Todo(2l, "待办任务", "程序说明备注"),
    Schedule(3l, "日程", "添加我的日程"),
    Customer(4l, "客户", ""),
    Contract(5l, "合同", ""),
    PriceSheet(6l, "报价单", ""),
    Refund(7l, "回款", ""),
    Action(8l, "客户行动", "行动备注说明"),
    Clue(9l, "销售线索", ""),
    Approve(10l, "审批", ""),
    Pool(11l, "公海", ""),
    Product(12l, "产品", "");

    /**
     * 最初的默认入口地址，
     */
    public static final List<QuickEntryEnum> DEFAULT_QUICK_ENTRIES = Arrays.asList(
            QuickEntryEnum.Todo,
            QuickEntryEnum.Schedule,
            QuickEntryEnum.Daily
    );

    private static final Map<Long, QuickEntryEnum> kv = new HashMap<>();
    static{
        Stream.of(QuickEntryEnum.values()).forEach(quickEntryEnum -> {
            kv.put(quickEntryEnum.getId(), quickEntryEnum);
        });
    }
    private final Long id;
    private final String display;
    private final String verboseDisplay;

    QuickEntryEnum(Long id, String display, String verboseDisplay) {
        this.id = id;
        this.display = display;
        this.verboseDisplay = verboseDisplay;
    }

    public Long getId() {
        return id;
    }

    public String getDisplay() {
        return display;
    }

    public String getVerboseDisplay() {
        return verboseDisplay;
    }

    public static QuickEntryEnum getById(Long id){
        return kv.get(id);
    }

}
