package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.api.ProductServerApi;
import com.zifangdt.ch.base.dto.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by 袁兵 on 2018/2/6.
 */
@Component
public class ProductVerboseFetcher extends AbstractVerboseFetcher<Long, Product> {

    @Autowired
    private ProductServerApi productServerApi;

    @Override
    protected List<Product> doFetch(Collection<Long> ids) {
        return productServerApi.getMultiProducts(new HashSet<>(ids));
    }

    @Override
    public String nameOf(Product verbose) {
        return verbose.getName();
    }
}
