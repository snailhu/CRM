package com.zifangdt.ch.base.bo.cfg.finance;

import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.pair.FinanceBizType;
import com.zifangdt.ch.base.enums.pair.RevenueOrExpense;
import com.zifangdt.ch.base.enums.pair.TraderType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/4/2.
 */
public class DetailOfJournalType extends DetailOfOption {
    private String description;
    private RevenueOrExpense belongsTo;
    private int sort;
    private Integer builtIn;
    private FinanceBizType bizType;
    private TraderType traderType;

    private static final Map<String, Long> IDS = new HashMap<>();

    static {
        IDS.put("合同分期回款", Constants.TYPE_OF_CONTRACT_PERIOD);
        IDS.put("工单回款", Constants.TYPE_OF_TICKET_REVENUE);
        IDS.put("采购支出", Constants.TYPE_OF_PURCHASE);
        IDS.put("工单结算", Constants.TYPE_OF_TICKET_EXPENSE);
        IDS.put("合作伙伴佣金", Constants.TYPE_OF_PARTNER_COMMISSION);
        IDS.put("合同提成", Constants.TYPE_OF_CONTRACT_COMMISSION);
        IDS.put("期初调整", Constants.TYPE_OF_ADJUST);
    }

    public TraderType getTraderType() {
        return traderType;
    }

    public void setTraderType(TraderType traderType) {
        this.traderType = traderType;
    }

    public FinanceBizType getBizType() {
        return bizType;
    }

    public void setBizType(FinanceBizType bizType) {
        this.bizType = bizType;
    }

    public Integer getBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(Integer builtIn) {
        this.builtIn = builtIn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RevenueOrExpense getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(RevenueOrExpense belongsTo) {
        this.belongsTo = belongsTo;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public List<ConfigItem> parse(ConfigType configType, List<String[]> lines) {
        return lines.stream().map(line -> {
            DetailOfJournalType journalType = new DetailOfJournalType();
            journalType.setName(line[0]);
            journalType.setBelongsTo(RevenueOrExpense.valueOf(line[1]));
            journalType.setDescription(line[2]);
            journalType.setSort(Integer.parseInt(line[3]));
            journalType.setDisabled(false);
            journalType.setBuiltIn(1);
            if (line.length >= 5) {
                journalType.setBizType(FinanceBizType.valueOf(line[4]));
                if (line.length >= 6) {
                    journalType.setTraderType(TraderType.valueOf(line[5]));
                }
            }

            ConfigItem item = new ConfigItem(configType, journalType);
            item.setId(IDS.get(journalType.getName()));
            return item;
        }).collect(Collectors.toList());
    }

    @Override
    public void preSave(ConfigType configType, ConfigItemMapper configItemMapper) {
        super.preSave(configType, configItemMapper);
        if (belongsTo == null) {
            throw new DataInvalidException("必须指定账目类型所属收入还是支出");
        }
        builtIn = null;
        bizType = null;
        traderType = null;
    }

    @Override
    public void preUpdate(ConfigType configType, ConfigItemMapper configItemMapper, BaseConfigDetail exist) {
        super.preUpdate(configType, configItemMapper, exist);
        DetailOfJournalType existType = (DetailOfJournalType) exist;
        if (existType.getBuiltIn() != null && existType.getBuiltIn().equals(1)) {
            belongsTo = null;
            setDisabled(null);
        }
        builtIn = null;
        bizType = null;
        traderType = null;
    }
}
