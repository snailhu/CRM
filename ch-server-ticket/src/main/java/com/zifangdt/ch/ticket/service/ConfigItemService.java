package com.zifangdt.ch.ticket.service;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.cfg.base.BaseConfigDetail;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfTicketType;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfUserChoice;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.dto.ticket.input.TicketTypeDto;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.ticket.TicketTypeSource;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.mapper.cfg.ConfigItemMapper;
import com.zifangdt.ch.base.service.cfg.AbstractConfigItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConfigItemService extends AbstractConfigItemService {
    @Autowired
    private ConfigItemMapper configItemMapper;

    @Autowired
    UaaServerApi uaaServerApi;

    private ConfigItem createTicketType(String name, TicketTypeSource source) {
        ConfigItem item = new ConfigItem();
        item.setType(ConfigType.ticketType);
        DetailOfTicketType detail = new DetailOfTicketType();
        detail.setName(name);
        detail.setTicketTypeSource(source);
        detail.setDeletable(false);
        item.setDetail(detail);
        return item;
    }

    @PostConstruct
    public void initTicketType() {
        if (!configItemMapper.nameExists(ConfigType.ticketType, "施工派单")) {
            ConfigItem item = createTicketType("施工派单", TicketTypeSource.CONTRACT_TYPE);
            configItemMapper.insert(item);
        }

        if (!configItemMapper.nameExists(ConfigType.ticketType, "售后")) {
            ConfigItem item = createTicketType("售后", TicketTypeSource.CONTRACT_TYPE);
            configItemMapper.insert(item);
        }

        if (!configItemMapper.nameExists(ConfigType.ticketType, "投诉")) {
            ConfigItem item = createTicketType("投诉", TicketTypeSource.STAFF_FEEDBACK);
            configItemMapper.insert(item);
        }
    }

    public void deleteTicketType(Long id) {
        ConfigItem configItem = configItemMapper.selectByPrimaryKey(id);
        if (configItem == null) return;
        DetailOfTicketType ticketType = (DetailOfTicketType) configItem.getDetail();
        if (!ticketType.getDeletable()) throw new DataInvalidException("该工单类型不能删除");
        configItemMapper.deleteByPrimaryKey(id);
    }

    public void updateTicketType(Long ticketTypeId, TicketTypeDto ticketTypeDto) {
        ConfigItem configItem = configItemMapper.selectByPrimaryKey(ticketTypeId);
        DetailOfTicketType ticketType = (DetailOfTicketType) configItem.getDetail();
        if (!ticketType.getDeletable()) {
            if (!ticketType.getName().equals(ticketTypeDto.getName())) {
                throw new DataInvalidException("不能修改该工单类型的名称");
            }
        }
        ticketType.setName(ticketTypeDto.getName());
        ticketType.setTicketTypeSource(ticketTypeDto.getTicketTypeSource());
        configItem.setDetail(ticketType);
        configItemMapper.updateByPrimaryKey(configItem);
    }

    public Set<Long> customerServiceStaff() {
        ConfigItem item = configItemMapper.getOneByType(ConfigType.customerServiceStaff);
        DetailOfUserChoice userChoice = (DetailOfUserChoice) item.getDetail();
        return userChoice.getUsers();
    }

    /**
     * 获取客服人员id和名称
     *
     * @return
     */
    public List<Map<String, Object>> getIdAndNameForCustomerStaff() {
        ConfigItem item = configItemMapper.getOneByType(ConfigType.customerServiceStaff);
        DetailOfUserChoice userChoice = (DetailOfUserChoice) item.getDetail();
        return userChoice.getUsers().stream()
                .map(id -> uaaServerApi.getUser(id))
                .map(user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", user.getId());
                    map.put("name", user.getName());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<IdAndName> ticketTypes() {
        List<BaseConfigDetail> details = details(ConfigType.ticketType);
        return details.stream().map(DetailOfTicketType.class::cast)
                .map(n -> new IdAndName(n.getId(), n.getName()))
                .collect(Collectors.toList());
    }
}
