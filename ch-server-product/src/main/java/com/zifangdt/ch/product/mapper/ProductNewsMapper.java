package com.zifangdt.ch.product.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.product.entity.ProductNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductNewsMapper extends BaseMapper<ProductNews> {
    List<ProductNews> findList(@Param("productId") Long productId, @Param("tag") String tag);
}
