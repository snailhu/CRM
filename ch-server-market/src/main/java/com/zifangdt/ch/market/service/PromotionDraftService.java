package com.zifangdt.ch.market.service;

import com.zifangdt.ch.base.dto.market.Promotion;
import com.zifangdt.ch.base.dto.market.PromotionDraft;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.service.DeleteServiceMixin;
import com.zifangdt.ch.market.bo.PromotionQueryBo;
import com.zifangdt.ch.market.mapper.PromotionDraftMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionDraftService extends BaseService<PromotionDraft, Long>
        implements DeleteServiceMixin{
    @Autowired
    PromotionDraftMapper promotionDraftMapper;

    @Override
    public PromotionDraftMapper getMapper() {
        return promotionDraftMapper;
    }

    List<PromotionDraft> findBy(PromotionQueryBo bo){
        return promotionDraftMapper.findBy(bo);
    }
}
