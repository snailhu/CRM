package com.zifangdt.ch.contract.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.contract.ProductDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/23.
 */
@Repository
public interface ProductDetailMapper extends BaseMapper<ProductDetail> {
    List<ProductDetail> findByProductId(@Param("productId") Long productId);
}
