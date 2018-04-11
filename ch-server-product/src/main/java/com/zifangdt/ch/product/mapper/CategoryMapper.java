package com.zifangdt.ch.product.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.product.entity.Category;
import com.zifangdt.ch.product.bo.CategoryQueryBo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category>{

    void create(Category category);

    List<Category> findBy(CategoryQueryBo bo);

    void updateNosByIds(@Param("orderedIds") List<Long> orderedIds);

    Category findOneByName(String name);

    boolean isSubCategoryExist(Long id);

    List<Category> findChildren(Long parentId);
}
