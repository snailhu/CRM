package com.zifangdt.ch.base.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.approval.RunningTrack;
import com.zifangdt.ch.base.dto.product.entity.Purchase;
import com.zifangdt.ch.base.dto.product.output.PurchaseInStockDto;
import com.zifangdt.ch.base.dto.ticket.output.PurchaseItemDetailDto;
import com.zifangdt.ch.base.enums.pair.ProcessStatus;

import javax.persistence.Transient;
import java.util.List;

import com.zifangdt.ch.base.dto.common.File;

@JsonIgnoreProperties(value = {"totalAmount"}, allowGetters = true)
public class PurchaseDetailDto extends Purchase {
    private List<PurchaseItemDetailDto> purchaseItemDtos;

    private String providerName;

    private ProcessOverview processOverview;

    private List<RunningTrack> tracks;

    private List<PurchaseInStockDto> inStocks;
    private List<File>  appendUrlDetail;

    private Integer cancelNum;

    public List<PurchaseItemDetailDto> getPurchaseItemDtos() {
        return purchaseItemDtos;
    }

    public PurchaseDetailDto setPurchaseItemDtos(List<PurchaseItemDetailDto> purchaseItemDtos) {
        this.purchaseItemDtos = purchaseItemDtos;
        return this;
    }
    @Transient
    @NamedProperty
    private ProcessStatus processStatus;


    public List<File> getAppendUrlDetail() {
        return appendUrlDetail;
    }

    public void setAppendUrlDetail(List<File> appendUrlDetail) {
        this.appendUrlDetail = appendUrlDetail;
    }

    public String getProviderName() {
        return providerName;
    }

    public PurchaseDetailDto setProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    public PurchaseDetailDto setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
        return this;
    }

    public ProcessOverview getProcessOverview() {
        return processOverview;
    }

    public PurchaseDetailDto setProcessOverview(ProcessOverview processOverview) {
        this.processOverview = processOverview;
        return this;
    }

    public List<RunningTrack> getTracks() {
        return tracks;
    }

    public PurchaseDetailDto setTracks(List<RunningTrack> tracks) {
        this.tracks = tracks;
        return this;
    }

    public List<PurchaseInStockDto> getInStocks() {
        return inStocks;
    }

    public PurchaseDetailDto setInStocks(List<PurchaseInStockDto> inStocks) {
        this.inStocks = inStocks;
        return this;
    }

    public Integer getCancelNum() {
        return cancelNum;
    }

    public void setCancelNum(Integer cancelNum) {
        this.cancelNum = cancelNum;
    }
}
