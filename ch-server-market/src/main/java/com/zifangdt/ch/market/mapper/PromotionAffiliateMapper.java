package com.zifangdt.ch.market.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.market.PromotionAffiliate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PromotionAffiliateMapper extends BaseMapper<PromotionAffiliate> {
    List<PromotionAffiliate> findByPromotionId(Long id);
    PromotionAffiliate findByUserAndPromotion(@Param("userId") Long userId, @Param("promotionId") Long promotionId);
    Long getPvForPromotion(Long promotionId);
    List<PromotionAffiliate> findUserAffiliate(Long userId);
}