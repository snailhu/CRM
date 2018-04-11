package com.zifangdt.ch.contract.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.contract.Instalment;
import org.springframework.stereotype.Repository;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Repository
public interface InstalmentMapper extends BaseMapper<Instalment> {
    void deleteByContractId(Long contractId);
}
