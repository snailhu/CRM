package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.enums.pair.TaskStatus;

import javax.validation.constraints.NotNull;

public class InventoryBo {

  //项目id
   @NotNull
    private Long projectId;

    //支出
     private Double payOut;
     //收入
     private Double income;
     //利润
     private Double profit;
     //成本
     private Double selfCost;
     //盘点状态
     private TaskStatus.InventoryStatus inventoryStatus;

 public Double getSelfCost() {
  return selfCost;
 }

 public void setSelfCost(Double selfCost) {
  this.selfCost = selfCost;
 }

 public Long getProjectId() {
  return projectId;
 }

 public void setProjectId(Long projectId) {
  this.projectId = projectId;
 }

 public Double getPayOut() {
  return payOut;
 }

 public void setPayOut(Double payOut) {
  this.payOut = payOut;
 }

 public Double getIncome() {
  return income;
 }

 public void setIncome(Double income) {
  this.income = income;
 }

 public Double getProfit() {
  return profit;
 }

 public void setProfit(Double profit) {
  this.profit = profit;
 }



 public TaskStatus.InventoryStatus getInventoryStatus() {
  return inventoryStatus;
 }

 public void setInventoryStatus(TaskStatus.InventoryStatus inventoryStatus) {
  this.inventoryStatus = inventoryStatus;
 }
}