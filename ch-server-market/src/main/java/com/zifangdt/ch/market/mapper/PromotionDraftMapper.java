package com.zifangdt.ch.market.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.market.PromotionDraft;
import com.zifangdt.ch.market.bo.PromotionQueryBo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionDraftMapper extends BaseMapper<PromotionDraft> {
    List<PromotionDraft> findBy(PromotionQueryBo bo);
}