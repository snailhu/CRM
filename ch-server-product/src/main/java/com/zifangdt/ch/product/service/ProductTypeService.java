package com.zifangdt.ch.product.service;

import com.zifangdt.ch.base.dto.product.input.ProductTypeCreateDto;
import com.zifangdt.ch.base.dto.product.output.ProductTypeBrand;
import com.zifangdt.ch.base.dto.product.output.ProductTypeBrief;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.dto.product.entity.ProductType;
import com.zifangdt.ch.product.mapper.ProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService extends BaseService<ProductType, Long> {
    @Autowired
    ProductTypeMapper productTypeMapper;

    public Long create(ProductTypeCreateDto dto) {
        ProductType productType = dto.convertTo();
        productType.setSort(productTypeMapper.getMaxSort() + 1);
        save(productType);
        return productType.getId();
    }

    public List<ProductTypeBrief> getList(String name) {
        List<ProductType> list = productTypeMapper.getByName(name);
        return list.stream().map(ProductTypeBrief::new).collect(Collectors.toList());
    }

    public void deleteOne(Long id) {
        if (productTypeMapper.isProductOfTypeExist(id)){
            throw new DataInvalidException("类目下还有产品，请先移动产品分类");
        }
        productTypeMapper.deleteByPrimaryKey(id);
    }

    public void transferProduct(Long sourceId, Long destId){
        productTypeMapper.changeProductTypeId(sourceId, destId);
    }

    /**
     * 把id=id的ProductType调整到id=afterId的ProductType后
     * @param id
     * @param afterId
     */
    public void insertAfter(Long id, Long afterId) {
        List<ProductType> less = productTypeMapper.getLess(id, afterId);
        List<ProductType> great = productTypeMapper.getGreat(id, afterId);
        ProductType csd = get(id);
        int sort = 1;
        for (ProductType c : less) {
            c.setSort(sort);
            updateInternal(c);
            sort += 1;
        }
        csd.setSort(sort);
        sort += 1;
        updateInternal(csd);
        for (ProductType c : great) {
            c.setSort(sort);
            updateInternal(c);
            sort += 1;
        }
    }

    public List<ProductTypeBrand> getTypeBrand() {
        return productTypeMapper.getTypeBrand();
    }
}
