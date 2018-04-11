package com.zifangdt.ch.contract.web;

import com.zifangdt.ch.base.bo.*;
import com.zifangdt.ch.base.converter.SupportFeignVerbose;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.contract.ProductDetail;
import com.zifangdt.ch.base.dto.product.entity.Product;
import com.zifangdt.ch.contract.bo.*;
import com.zifangdt.ch.contract.service.ContractService;
import com.zifangdt.ch.contract.service.NewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@RestController
@RequestMapping("/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private NewsService newsService;

    @PostMapping
    public Long save(@Valid @RequestBody CreateBo createBo) {
        Contract contract = new Contract();
        BeanUtils.copyProperties(createBo, contract);
        contractService.save(contract);
        return contract.getId();
    }

    @GetMapping("/{id}")
    public Contract detail(@PathVariable("id") Long id) {
        return contractService.getDetail(id);
    }

    @PostMapping("/{id}")
    @SupportFeignVerbose
    public Contract detailFromProcessModule(@PathVariable("id") Long id, @RequestBody ProcessOverview processOverview) {
        return contractService.getDetailFromProcessModule(id, processOverview);
    }

    @GetMapping("/byIds")
    public List<Contract> getDetails(@RequestParam("ids") Set<Long> ids) {
        return contractService.getDetails(ids);
    }

    @GetMapping
    public PageResultBo<Contract> findListForWeb(@Valid QueryBo queryBo) {
        return contractService.findListForWeb(queryBo);
    }

    @PutMapping("/{id}")
    public void invalid(@PathVariable("id") Long id, @RequestBody InvalidBo bo) {
        contractService.invalid(id, bo.getReason());
    }

    @PutMapping
    public void invalid(@RequestBody BatchInvalidBo bo) {
        contractService.invalidBatch(bo.getIds(), bo.getReason());
    }

    @PutMapping("/{id}/basic")
    public void updateBasic(@PathVariable("id") Long id, @Valid @RequestBody CreateBo createBo) {
        contractService.update(id, createBo);
    }

    @PutMapping("/{id}/productDetail")
    public void updateProductDetail(@PathVariable("id") Long id, @Valid @RequestBody ProductDetail bo) {
        contractService.saveOrUpdateProductDetail(id, bo);
    }

    @PutMapping("/{id}/finance")
    public void updateFinance(@PathVariable("id") Long id, @Valid @RequestBody ApproveFinanceBo bo) {
        contractService.update(id, bo);
    }

    @PostMapping("/callbackAfterApproved")
    public void callbackAfterApproved(@RequestBody ProcessInstance processInstance) {
        contractService.callbackAfterApproved(processInstance);
    }

    @PutMapping("/{id}/owner/{userId}")
    public void changeOwner(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        contractService.changeOwner(id, userId);
    }

    @GetMapping("/{id}/news")
    public PageResultBo<News> newsForWeb(@PathVariable("id") Long id, NewsPageQueryBo queryBo) {
        queryBo.setRelatedId(id);
        return newsService.findListForWeb(queryBo);
    }

    @GetMapping("/{id}/check")
    public void beforeRedirect(@PathVariable("id") Long id) {
        contractService.check(id);
    }

    @GetMapping("/approved")
    public PageResultBo<Contract> approved(ApprovedContractQueryBo queryBo) {
        return contractService.findApproved(queryBo);
    }

    @GetMapping("/{id}/products")
    public List<Product> products(@PathVariable("id") Long id) {
        return contractService.products(id);
    }

    @GetMapping(value = "/byProjectId", params = "for=internal")
    public Contract getByProjectId(@RequestParam("projectId") Long projectId) {
        return contractService.getDetailByProjectId(projectId);
    }

    @GetMapping("/productSale/{id}")
    public Long getProductSaleCount(@PathVariable("id") Long productId) {
        return contractService.getProductSaleCount(productId);
    }

    @GetMapping(value = "/{id}/withCustomer", params = "for=internal")
    @SupportFeignVerbose
    public Contract withCustomer(@PathVariable("id") Long contractId) {
        return contractService.contractWithCustomer(contractId);
    }

    @GetMapping(value = "/existsCustomer", params = "for=internal")
    public BooleanBo existsCustomer(@RequestParam("customerId") Long customerId) {
        return new BooleanBo(contractService.existsCustomer(customerId));
    }

    @GetMapping("/byCustomerId")
    public PageResultBo<Contract> findByCustomerId(ByCustomerIdQueryBo queryBo) {
        return contractService.findByCustomerId(queryBo);
    }

    @GetMapping(value = "/signedCustomerIds", params = "for=internal")
    public List<Long> findSignedCustomerIdsInDays(@RequestParam("days") int days) {
        return contractService.findSignedCustomerIdsInDays(days);
    }

    @GetMapping(value = "/countByCustomerId", params = "for=internal")
    public long countByCustomerId(@RequestParam("customerId") Long customerId) {
        return contractService.countByCustomerId(customerId);
    }

    @GetMapping(value = "/{id}/printNumber", params = "for=internal")
    public String printNumber(@PathVariable("id") Long id) {
        return contractService.get(id).getPrintNumber();
    }

    @GetMapping(value = "/repurchasedCustomerIds", params = "for=internal")
    public List<Long> findRepurchasedCustomerIds() {
        return contractService.findRepurchasedCustomerIds();
    }

    @GetMapping(value = "/{id}/simple", params = "for=internal")
    public Contract simple(@PathVariable("id") Long id) {
        return contractService.get(id);
    }

}
