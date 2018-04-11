package com.zifangdt.ch.product.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.api.ContractServerApi;
import com.zifangdt.ch.base.dto.product.entity.Category;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.base.dto.product.entity.ProductNews;
import com.zifangdt.ch.base.dto.product.entity.Repo;
import com.zifangdt.ch.base.dto.product.entity.RepoProduct;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.service.DeleteServiceMixin;
import com.zifangdt.ch.product.bo.ProductQueryBo;
import com.zifangdt.ch.base.dto.product.CategoryDto;
import com.zifangdt.ch.product.event.ProductCreateEvent;
import com.zifangdt.ch.product.event.ProductUpdateEvent;
import com.zifangdt.ch.product.mapper.ProductMapper;
import com.zifangdt.ch.product.mapper.ProductNewsMapper;
import com.zifangdt.ch.product.mapper.RepoProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService<Product, Long> implements DeleteServiceMixin{
    @Autowired
    ProductMapper productMapper;

    @Autowired
    RepoProductMapper repoProductMapper;

    @Autowired
    RepoService repoService;

    @Autowired
    RepoProductService repoProductService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ProductNewsMapper productNewsMapper;

    @Autowired
    ContractServerApi contractServerApi;

    @Autowired
    StockChangeLogService stockChangeLogService;

    @Autowired
    OutStockService outStockService;

    /**
     * 根据查询条件获取所有产品，并分页
     *
     * @param bo
     * @return
     */
    public Page<Product> getProductListPage(ProductQueryBo bo) {
        Page<Product> page = PageHelper
                .startPage(bo.getPage(), bo.getSize())
                .doSelectPage(() -> productMapper.queryBy(bo));
        Map<Long, Long> signedNumber = outStockService.getPreOutProductNumber();
        for (Product product: page) {
            product.setSoldNumber(contractServerApi.getProductSaleCount(product.getId()));
            product.setSignedNumber(signedNumber.getOrDefault(product.getId(), 0l));
        }
        return page;
    }

    public List<Product> getProductList(ProductQueryBo bo){
        return productMapper.queryBy(bo);
    }

    public Product getProductDetail(Long id) {
        Product product = productMapper.queryDetail(id);
        return product;
    }

    /**
     * 获取产品在正品仓库库存
     * @param productId
     * @return
     */
    public Integer getProductNormalStockNumber(Long productId) {
        return productMapper.getNormalStockNumber(productId);
    }
    public void batchDelete(List<Long> ids) {
        if (!ids.isEmpty()) {
            productMapper.batchDelete(ids);
            repoProductMapper.batchDelete(ids);
        }
    }

    @Override
    public void save(Product toBeSaved) {
        super.save(toBeSaved);
        if (toBeSaved.getRepoNumber() != null && toBeSaved.getRepoNumber() > 0) {
            Repo repo = repoService.getDefaultRepo();
            RepoProduct repoProduct = new RepoProduct();
            repoProduct.setNumber(toBeSaved.getRepoNumber());
            repoProduct.setAvailableNumber(repoProduct.getNumber());
            repoProduct.setRepoId(repo.getId());
            repoProduct.setProductId(toBeSaved.getId());
            repoProductService.save(repoProduct);
        }
        applicationEventPublisher.publishEvent(new ProductCreateEvent(this, toBeSaved));
    }

    public void toggleActive(long id) {
        productMapper.toggleActive(id);
    }

    public void batchUpdateCategory(List<Long> productIds, Long categoryId) {
        categoryService.get(categoryId);
        productMapper.batchChangeCategory(productIds, categoryId);
    }

    @Override
    public ProductMapper getMapper() {
        return productMapper;
    }

    public List<ProductNews> findNews(Long id, String tag) {
        List<ProductNews> list = productNewsMapper.findList(id, tag);
        Set<Long> ids = list.stream().map(ProductNews::getOperator).collect(Collectors.toSet());
        return list;
    }

    @Override
    public void postUpdate(Product stored, Product updated, Class<?> Product) {
        applicationEventPublisher.publishEvent(new ProductUpdateEvent(this, updated));
    }

    public Product findByNumber(String number){
        return productMapper.findByNumber(number);
    }

    /**
     * 获取类目和产品，以树型展示
     * @return
     */
    public List<CategoryDto> getCategoryAndProduct() {
        List<Category> list = categoryService.findAll();
        Map<Long, CategoryDto> map = new HashMap<>();
        for (Category category: list) {
            CategoryDto categoryDto = new CategoryDto(category);
            map.put(category.getId(), categoryDto);
        }

        List<Product> products = findAll();
        for (Product product: products) {
            CategoryDto dto = map.getOrDefault(product.getCategoryId(), null);
            if (dto != null) {
                List<Product> products1 = dto.getProducts();
                if (products1 == null) {
                    products1 = new ArrayList<>();
                }
                products1.add(product);
                dto.setProducts(products1);
            }
        }
        List<CategoryDto> result = categoryService.buildTree(map);
        return result;
    }

    public void setProductAlertNumber(Long productId, Integer number) {
        Product product = get(productId);
        if (product == null) throw new DataNotFoundException("产品不存在");
        product.setAlertNumber(number);
        updateInternal(product);
    }

    public void setProductsAlertNumber(List<Long> productIds, Integer number) {
        if (!productIds.isEmpty()) {
            productMapper.updateAlertNumber(productIds, number);
        }
    }

    public List<Product> findByIds(Set<Long> ids) {
        return productMapper.findByIds(ids);
    }

    public Map<Long, Integer> getProductAlertNumber() {
        List<Product> products = findAll();
        return products.stream().collect(Collectors.toMap(Product::getId, Product::getAlertNumber));
    }
}
