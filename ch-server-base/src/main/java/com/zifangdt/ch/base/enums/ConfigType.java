package com.zifangdt.ch.base.enums;

import com.zifangdt.ch.base.bo.cfg.base.*;
import com.zifangdt.ch.base.bo.cfg.contract.DetailOfContractInvalid;
import com.zifangdt.ch.base.bo.cfg.contract.DetailOfContractType;
import com.zifangdt.ch.base.bo.cfg.contract.DetailOfProcedure;
import com.zifangdt.ch.base.bo.cfg.customer.*;
import com.zifangdt.ch.base.bo.cfg.finance.DetailOfJournalType;
import com.zifangdt.ch.base.bo.cfg.finance.DetailOfPaymentWay;
import com.zifangdt.ch.base.bo.cfg.projectManager.DetailOfProjectCloseUser;
import com.zifangdt.ch.base.bo.cfg.projectManager.DetailOfProjectMessageUser;

/**
 * Created by 袁兵 on 2018/1/6.
 */
public enum ConfigType {
    //财务
    paymentWays(Module.FINANCE, true, DetailOfPaymentWay.class),
    financialStaff(Module.FINANCE, false, DetailOfUserChoice.class),
    journalTypes(Module.FINANCE, true, DetailOfJournalType.class),

    //合同
    contractFlowSteps(Module.CONTRACT, true, DetailOfFlowStep.class),
    revenueRecordPermission(Module.CONTRACT, false, DetailOfUserChoice.class),
    contractInvalidPermission(Module.CONTRACT, false, DetailOfContractInvalid.class),
    contractNoticePermission(Module.CONTRACT, false, DetailOfUserChoice.class),
    contractTypes(Module.CONTRACT, true, DetailOfContractType.class),
    procedures(Module.CONTRACT, true, DetailOfProcedure.class),

    //客户
    customerInvalidPermission(Module.CUSTOMER, false, DetailOfUserChoiceWithOwner.class),
    customerLimit(Module.CUSTOMER, false, DetailOfCustomerLimit.class),
    publicCustomerRule(Module.CUSTOMER, false, DetailOfPublicCustomer.class),
    customerSources(Module.CUSTOMER, true, DetailOfCustomerSource.class),
    publicCustomerInvalidPermission(Module.CUSTOMER, false, DetailOfUserChoiceWithOwner.class),
    customizedData(Module.CUSTOMER, true, DetailOfCustomizedData.class),
    residentialArea(Module.CUSTOMER, false, DetailOfResidentialArea.class),

    //报价单
    quoteFlowSteps(Module.QUOTE, true, DetailOfFlowStep.class),

    //项目
    projectFlowSteps(Module.PROJECTMANAGER, true, DetailOfFlowStep.class),
    projectManager(Module.PROJECTMANAGER, false, DetailOfUserChoice.class),
    projectClosePermission(Module.PROJECTMANAGER, false, DetailOfProjectCloseUser.class),
    projectMessageReceiver(Module.PROJECTMANAGER, false, DetailOfProjectMessageUser.class),

    // 产品和采购
    RepoStaff(Module.PRODUCT, false, DetailOfUserChoice.class),
    purchaseFlowSteps(Module.PRODUCT, true, DetailOfFlowStep.class),
    STOCK_ALERT_STAFF(Module.PRODUCT, false, DetailOfUserChoice.class), // 库存预警推送的用户

    // 工单
    customerServiceStaff(Module.TICKET, false, DetailOfUserChoice.class),
    ticketPermission(Module.TICKET, false, DetailOfUserChoice.class),
    ticketType(Module.TICKET, true, DetailOfTicketType.class);

    private Module module;
    private final boolean multi;
    private final Class<? extends BaseConfigDetail> boClass;

    ConfigType(Module module, boolean multi, Class<? extends BaseConfigDetail> boClass) {
        this.module = module;
        this.multi = multi;
        this.boClass = boClass;
    }

    public Module getModule() {
        return module;
    }

    public Class<? extends BaseConfigDetail> getBoClass() {
        return boClass;
    }

    public boolean isMulti() {
        return multi;
    }

}
