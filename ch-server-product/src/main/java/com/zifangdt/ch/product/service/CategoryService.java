package com.zifangdt.ch.product.service;

import com.zifangdt.ch.base.dto.product.entity.Category;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.service.DeleteServiceMixin;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.product.bo.CategoryQueryBo;
import com.zifangdt.ch.base.dto.product.CategoryCreateDto;
import com.zifangdt.ch.base.dto.product.CategoryDto;
import com.zifangdt.ch.product.mapper.CategoryMapper;
import com.zifangdt.ch.product.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService extends BaseService<Category, Long> implements DeleteServiceMixin{
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductMapper productMapper;

    public Category create(CategoryCreateDto categoryCreateDto){
        Category category = categoryCreateDto.convertTo();
        Category parentCategory = get(category.getParentId());
        if (parentCategory == null) {
            category.setParentPath("/");
        } else {
            category.setParentPath(parentCategory.getParentPath() + parentCategory.getId() + "/");
        }
        category.setCreateId(CurrentUser.getUserId());
        categoryMapper.create(category);
        return category;
    }

    public List<CategoryDto> findTree(String name) {
        CategoryQueryBo bo = new CategoryQueryBo();
        bo.setName(name);
        List<Category> list = categoryMapper.findBy(bo);
        Map<Long, CategoryDto> map = new HashMap<>();
        for (Category category: list) {
            category.setCount(productMapper.getCategoryCount(category.getId()));
            CategoryDto categoryDto = new CategoryDto(category);
            map.put(category.getId(), categoryDto);
        }
        return buildTree(map);
    }

    public List<CategoryDto> buildTree(Map<Long, CategoryDto> map){
        List<CategoryDto> result = new ArrayList<>();
        for (Long id: map.keySet()) {
            CategoryDto categoryDto = map.get(id);
            if (categoryDto.getParentId() == null) result.add(categoryDto);
            else {
                CategoryDto parent = map.get(categoryDto.getParentId());
                if (parent != null) {
                    parent.addChild(categoryDto);
                } else {
                    result.add(categoryDto);
                }
            }
        }
        return result;
    }

    public List<CategoryDto> findBy(CategoryQueryBo bo){
        List<Category> list = categoryMapper.findBy(bo);
        List<CategoryDto> result = new ArrayList<>();
        for(Category category: list){
            category.setCount(productMapper.getCategoryCount(category.getId()));
        }
        for(Category category: list){
            result.add(new CategoryDto(category));
        }
        return result;
    }

    public void delete(Long id){
        if (productMapper.isProductCategoryExist(id)){
            throw new DataInvalidException("类目下还有产品，请先移动产品分类");
        }
        if (categoryMapper.isSubCategoryExist(id)) {
            throw new DataInvalidException("类目下还有子类目，请先删除子类目");
        }
        DeleteServiceMixin.super.delete(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    public void transferProduct(Long sourceId, Long destId){
        if (categoryMapper.isSubCategoryExist(sourceId)) {
            throw new DataInvalidException("不能从大类目转移产品，请选择子类目");
        }
        if (categoryMapper.isSubCategoryExist(destId)) {
            throw new DataInvalidException("不能从大类目转移产品，请选择子类目");
        }
        productMapper.changeCategoryId(sourceId, destId);
    }

    @Override
    public CategoryMapper getMapper() {
        return categoryMapper;
    }

    @Cacheable(cacheNames = "categoryByName", cacheManager = "requestCacheManager")
    public Category getOne(String categoryName) {
        return categoryMapper.findOneByName(categoryName);
    }

    /**
     * 是否是子类目
     * @param id
     * @return
     */
    public Boolean isSubCategoryExist(Long id){
        return categoryMapper.isSubCategoryExist(id);
    }

    public void transferCategory(Long sourceId, Long destId) {
        Category source = get(sourceId);
        if (source == null) throw new DataInvalidException("源类目不存在");
        Category dest = get(destId);
        if (dest == null) throw new DataInvalidException("目的类目不存在");
        if (dest.getParentPath().contains(String.valueOf(sourceId))) throw new DataInvalidException("不能转移到子类目");

        List<Category> children = categoryMapper.findChildren(sourceId);
        for (Category category: children) {
            if (category.getParentId().equals(sourceId)) {
                category.setParentId(destId);
                if (dest.getParentPath() == null || dest.getParentPath().isEmpty() || dest.getParentPath().equals("/")) {
                    category.setParentPath("/" + destId + "/");
                } else {
                    category.setParentPath(dest.getParentPath() + destId + "/");
                }
            } else {
                String parentPath = category.getParentPath();
                String[] result = parentPath.split(String.valueOf(sourceId));
                category.setParentPath(dest.getParentPath() + dest.getId() + result[1]);
            }
            updateInternal(category);
        }
    }
}
