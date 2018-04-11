package com.zifangdt.ch.finance.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.finance.AccountTransfer;
import org.springframework.stereotype.Repository;

/**
 * Created by 袁兵 on 2018/4/10.
 */
@Repository
public interface AccountTransferMapper extends BaseMapper<AccountTransfer> {
    AccountTransfer getDetail(Long id);
}
