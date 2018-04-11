package com.zifangdt.ch.finance.service;

import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.IdAndNameOfJournalType;
import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfOption;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfUserChoice;
import com.zifangdt.ch.base.bo.cfg.finance.DetailOfJournalType;
import com.zifangdt.ch.base.bo.cfg.finance.DetailOfPaymentWay;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import com.zifangdt.ch.finance.mapper.LocalConfigItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/6.
 */
@Service
public class ConfigItemService extends AbstractConfigItemService {
    @Autowired
    private ConfigItemMapper configItemMapper;
    @Autowired
    private LocalConfigItemMapper localConfigItemMapper;
    private static final List<String> HIDDEN_OPTIONS = Arrays.asList(
            "期初调整"
    );

    @Override
    protected void preUpdate(ConfigType configType, BaseConfigDetail param, BaseConfigDetail exist) {
        if (configType == ConfigType.journalTypes && localConfigItemMapper.journalTypeUsed(exist.getId())
                || (configType == ConfigType.paymentWays && localConfigItemMapper.paymentWayUsed(exist.getId()))) {
            ((DetailOfJournalType) param).setDisabled(null);
        }
    }

    @Override
    protected void preDeleteOption(DetailOfOption option) {
        if (option instanceof DetailOfJournalType) {
            if (localConfigItemMapper.journalTypeUsed(option.getId())) {
                throw new WrongOperationException("无法删除已被使用的账目类型");
            }
            DetailOfJournalType type = (DetailOfJournalType) option;
            if (type.getBuiltIn() != null && type.getBuiltIn().equals(1)) {
                throw new WrongOperationException("无法删除预设的账目类型");
            }
        } else if (option instanceof DetailOfPaymentWay) {
            if (localConfigItemMapper.paymentWayUsed(option.getId())) {
                throw new WrongOperationException("无法删除已被使用的结算类型");
            }
        }
    }

    @Override
    protected List<ConfigItem> getAll() {
        return localConfigItemMapper.getAllWithWhetherUsed();
    }

    public Set<Long> financialStaff() {
        ConfigItem item = configItemMapper.getOneByType(ConfigType.financialStaff);
        DetailOfUserChoice userChoice = (DetailOfUserChoice) item.getDetail();
        return userChoice.getUsers();
    }

    @Override
    protected IdAndName fromOption(DetailOfOption option) {
        if (option instanceof DetailOfJournalType) {
            DetailOfJournalType type = (DetailOfJournalType) option;
            IdAndNameOfJournalType n = new IdAndNameOfJournalType();
            n.setId(option.getId());
            n.setName(option.getName());
            n.setBelongsTo(type.getBelongsTo());
            return n;
        } else {
            return super.fromOption(option);
        }
    }

    @Override
    protected boolean optionFilter(IdAndName idAndName) {
        return !HIDDEN_OPTIONS.contains(idAndName.getName());
    }
}
