package com.zifangdt.ch.product.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.product.entity.Dealer;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.product.bo.ProviderQueryBo;
import com.zifangdt.ch.product.mapper.DealerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealerService extends BaseService<Dealer, Long>{
    @Autowired
    DealerMapper dealerMapper;

    @Autowired
    UaaServerApi uaaServerApi;

    public Page<Dealer> findBy(ProviderQueryBo bo) {
        List<Long> userIds = new ArrayList<>();
        if (bo.getName() != null) {
            List<User> users = uaaServerApi.internalFindBy(bo.getName());
            userIds = users.stream().map(User::getId).collect(Collectors.toList());
        }
        List<Long> finalUserIds = userIds;
        return PageHelper
                .startPage(bo.getPage(), bo.getSize())
                .doSelectPage(() -> dealerMapper.findBy(bo.getName(), finalUserIds.size() != 0? finalUserIds : null));
    }

    public void deleteOne(Long id) {
        dealerMapper.deleteOne(id);
    }

    public void deleteMulti(List<Long> ids) {
        if (!ids.isEmpty()) {
            dealerMapper.deleteMulti(ids);
        }
    }
}
