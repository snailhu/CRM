package com.zifangdt.ch.product.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.product.entity.ProductType;
import com.zifangdt.ch.base.dto.product.output.ProductTypeBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTypeMapper extends BaseMapper<ProductType>{

    Integer getMaxSort();

    List<ProductType> getByName(@Param("name") String name);

    List<ProductType> getLess(@Param("id") Long id, @Param("afterId") Long afterId);

    List<ProductType> getGreat(@Param("id") Long id, @Param("afterId") Long afterId);

    List<ProductTypeBrand> getTypeBrand();

    boolean isProductOfTypeExist(Long id);

    void changeProductTypeId(@Param("sourceId") Long sourceId, @Param("destId") Long destId);
}