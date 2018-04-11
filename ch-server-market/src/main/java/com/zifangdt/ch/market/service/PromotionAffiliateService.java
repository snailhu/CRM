package com.zifangdt.ch.market.service;

import com.zifangdt.ch.base.dto.market.Promotion;
import com.zifangdt.ch.base.dto.market.PromotionAffiliate;
import com.zifangdt.ch.base.dto.market.PromotionJoin;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.market.event.PromotionAffiliateCreateEvent;
import com.zifangdt.ch.market.mapper.PromotionAffiliateMapper;
import com.zifangdt.ch.market.mapper.PromotionJoinMapper;
import com.zifangdt.ch.market.mapper.PromotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionAffiliateService extends BaseService<PromotionAffiliate, Long>{

    @Autowired
    PromotionAffiliateMapper promotionAffiliateMapper;

    @Autowired
    PromotionJoinMapper promotionJoinMapper;

    @Autowired
    PromotionMapper promotionMapper;

    @Value("${PROMOTION_URL}")
    String promotionUrl;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public List<PromotionAffiliate> getUserAffiliates(Long userId){
        return promotionAffiliateMapper.findUserAffiliate(userId);
    }

    public PromotionAffiliate getAffiliateDetail(Long affiliateId){
        PromotionAffiliate affiliate = get(affiliateId);
        if (affiliate == null){
            throw new DataNotFoundException("not found");
        }
        Promotion promotion = promotionMapper.selectById(affiliate.getPromotionId());
        affiliate.setPromotionName(promotion.getName());
//        affiliate.setUrl(promotionUrl + "?p=" + promotion.getId() + "&u=" + affiliate.getUserId());
        affiliate.updateUrl(promotionUrl);
        List<PromotionJoin> joins = promotionJoinMapper.findByUserAndPromotion(
                affiliate.getUserId(), affiliate.getPromotionId());
        affiliate.setJoinList(joins);
        return affiliate;
    }

    @Override
    protected void postSave(PromotionAffiliate saved) {
        applicationEventPublisher.publishEvent(new PromotionAffiliateCreateEvent(this, saved));
    }
}
