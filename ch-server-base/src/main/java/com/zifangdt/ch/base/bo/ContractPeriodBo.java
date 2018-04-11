package com.zifangdt.ch.base.bo;

import com.zifangdt.ch.base.dto.contract.Contract;

import java.util.Map;

/**
 * Created by 袁兵 on 2018/1/31.
 */
public class ContractPeriodBo {
    private Contract contract;
    private Map<Long,String> optionNames;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Map<Long, String> getOptionNames() {
        return optionNames;
    }

    public void setOptionNames(Map<Long, String> optionNames) {
        this.optionNames = optionNames;
    }
}
