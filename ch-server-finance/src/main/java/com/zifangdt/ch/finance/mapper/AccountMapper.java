package com.zifangdt.ch.finance.mapper;

import com.zifangdt.ch.base.bo.PageQueryBo;
import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.finance.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 袁兵 on 2018/4/2.
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {
    List<Account> getList(PageQueryBo queryBo);

    List<Account> getListForSumMoney();

    boolean hasBeenUsed(Long id);
}
