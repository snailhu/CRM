package com.zifangdt.ch.market.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.market.Promotion;
import com.zifangdt.ch.market.bo.PromotionQueryBo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionMapper extends BaseMapper<Promotion> {
    List<Promotion> findBy(PromotionQueryBo promotionQueryBo);
    List<Promotion> findUserPromotion(Long userId);
    Promotion selectById(Long id);
}