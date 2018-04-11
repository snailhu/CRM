package com.zifangdt.ch.market.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.market.PromotionJoin;
import com.zifangdt.ch.market.bo.PromotionJoinQueryBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PromotionJoinMapper extends BaseMapper<PromotionJoin> {
    Long getJoinNumberForPromotion(Long promotionId);
    List<PromotionJoin> findByQuery(PromotionJoinQueryBo queryBo);
    PromotionJoin getDetail(Long id);
    List<PromotionJoin> findByUserAndPromotion(@Param("userId") Long userId,
                                               @Param("promotionId") Long promotionId);
    List<PromotionJoin> findByPromotion(Long promotionId);
    Boolean isJoinExist(@Param("promotionId") Long promotionId, @Param("info") String info);
}