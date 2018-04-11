package com.zifangdt.ch.product.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.product.entity.Provider;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.product.bo.ProviderQueryBo;
import com.zifangdt.ch.product.mapper.ProviderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProviderService extends BaseService<Provider, Long>{

    @Autowired
    ProviderMapper providerMapper;

    @Autowired
    UaaServerApi uaaServerApi;


    public Page<Provider> findBy(ProviderQueryBo bo) {
        List<Long> userIds = new ArrayList<>();
        if (bo.getName() != null) {
            List<User> users = uaaServerApi.internalFindBy(bo.getName());
            userIds = users.stream().map(User::getId).collect(Collectors.toList());
        }
        List<Long> finalUserIds = userIds;
        Page<Provider> page = PageHelper
                .startPage(bo.getPage(), bo.getSize())
                .doSelectPage(() -> providerMapper.findBy(bo.getName(), finalUserIds.size() != 0? finalUserIds : null));
        return page;
    }

    public void deleteOne(Long id) {
        providerMapper.deleteOne(id);
    }

    public void deleteMulti(List<Long> ids) {
        if (!ids.isEmpty()) {
            providerMapper.deleteMulti(ids);
        }
    }
}
