package com.zifangdt.ch.market.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.api.CustomerServerApi;
import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.bo.CustomerCreateBo;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.dto.market.PromotionJoin;
import com.zifangdt.ch.base.dto.todo.TodoTask;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.PromotionJoinStatusEnum;
import com.zifangdt.ch.base.enums.pair.CustomerSourceRelationType;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.market.api.TodoServerApi;
import com.zifangdt.ch.market.bo.PromotionJoinQueryBo;
import com.zifangdt.ch.market.event.PromotionJoinCreateEvent;
import com.zifangdt.ch.market.mapper.PromotionJoinMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PromotionJoinService extends BaseService<PromotionJoin, Long> {

    @Autowired
    PromotionJoinMapper promotionJoinMapper;

    @Autowired
    UaaServerApi uaaServerApi;

    @Autowired
    TodoServerApi todoServerApi;

    @Autowired
    CustomerServerApi customerServerApi;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public Page<PromotionJoin> findByQuery(PromotionJoinQueryBo queryBo) {
        if (queryBo.getSearch() != null && !queryBo.getSearch().isEmpty()) {
            List<User> users = uaaServerApi.internalFindBy(queryBo.getSearch());
            if (!users.isEmpty()) queryBo.setOwnerId(users.get(0).getId());
        }
        Page<PromotionJoin> page = PageHelper
                .startPage(queryBo.getPage(), queryBo.getSize())
                .doSelectPage(() -> promotionJoinMapper.findByQuery(queryBo));
        return page;
    }

    public PromotionJoin getOne(Long promotionJoinId) {
        PromotionJoin promotionJoin = promotionJoinMapper.getDetail(promotionJoinId);
        return promotionJoin;
    }

    public PromotionJoin addToCustomer(Long id, Customer customer, Boolean isPool) throws Exception{
        PromotionJoin promotionJoin = get(id);
        if (promotionJoin == null) throw new DataNotFoundException();

        CustomerCreateBo bo = new CustomerCreateBo();
        if (promotionJoin.getStatus() != PromotionJoinStatusEnum.Unhandled)
            throw new DataInvalidException("已经被处理了");
        bo.setName(promotionJoin.getName());
        bo.setPhone(promotionJoin.getPhone());
        bo.setAddress(promotionJoin.getName());
        Long customerSourceTypeId = null;
        List<Map<String, Object>> customerSources = customerServerApi.byType(ConfigType.customerSources);
        for (Map<String, Object> map: customerSources) {
            if (((Integer)map.get("relationType")).equals(CustomerSourceRelationType.MARKET.getIntVerifier())) {
                customerSourceTypeId = ((Integer)map.get("id")).longValue();
                break;
            }
        }
        if (customerSourceTypeId == null) throw new DataInvalidException("没有市场活动客户类型");
        bo.setSource(customerSourceTypeId);
        bo.setSourceRelationId(promotionJoin.getPromotionId());

        Long customerId = customerServerApi.save(bo);
        promotionJoin.setRelateId(customerId);
        promotionJoin.setStatus(PromotionJoinStatusEnum.AddToCustomer);
        updateInternal(promotionJoin);
        return promotionJoin;
    }

    public PromotionJoin addToTodo(Long id, TodoTask todo) {
        PromotionJoin promotionJoin = get(id);
        if (promotionJoin == null) throw new DataNotFoundException();
        if (promotionJoin.getStatus() != PromotionJoinStatusEnum.Unhandled)
            throw new DataInvalidException("已经被处理了");
        if (todo.getOperator() == null) {
            todo.setOperator(promotionJoin.getOwnerId());
        }
        todo.setType(com.zifangdt.ch.base.enums.pair.TaskType.TODO);
        Long todoId = todoServerApi.save(todo);

        promotionJoin.setRelateId(todoId);
        promotionJoin.setStatus(PromotionJoinStatusEnum.AddToTodo);
        updateInternal(promotionJoin);
        return promotionJoin;
    }

    @Override
    protected void postSave(PromotionJoin saved) {
        applicationEventPublisher.publishEvent(new PromotionJoinCreateEvent(this, saved));
    }
}
