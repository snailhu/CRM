package com.zifangdt.ch.base.dto.contract;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.VerboseProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Table(name = "biz_contract_product_detail")
public class ProductDetail implements Serializable {
    private static final long serialVersionUID = 2387267201671726435L;
    @Id
    private Long contractId;
    @NotEmpty
    @Valid
    private MultiProduct[] products;
    @VerboseProperty(target = JsonPropertyTarget.FILE)
    private List<String> attachments;
    private String remark;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public MultiProduct[] getProducts() {
        return products;
    }

    public void setProducts(MultiProduct[] products) {
        this.products = products;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class MultiProduct {
        @NotNull
        @VerboseProperty(target = JsonPropertyTarget.PRODUCT)
        private Long productId;
        @NotNull
        @Min(value = 1, message = "产品数量必须至少为1")
        private Integer amount;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }
    }
}
