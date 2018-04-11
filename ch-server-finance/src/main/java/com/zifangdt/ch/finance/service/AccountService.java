package com.zifangdt.ch.finance.service;

import com.zifangdt.ch.base.bo.IdAndNameOfAccount;
import com.zifangdt.ch.base.bo.PageQueryBo;
import com.zifangdt.ch.base.bo.PageResultBo;
import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dto.finance.Account;
import com.zifangdt.ch.base.dto.finance.AccountTransfer;
import com.zifangdt.ch.base.dto.finance.Journal;
import com.zifangdt.ch.base.enums.pair.JournalStatus;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.finance.bo.AccountAdjust;
import com.zifangdt.ch.finance.bo.AccountSumMoney;
import com.zifangdt.ch.finance.mapper.AccountMapper;
import com.zifangdt.ch.finance.mapper.AccountTransferMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/4/2.
 */
@Service
public class AccountService extends BaseService<Account, Long> {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private JournalService journalService;
    @Autowired
    private AccountTransferMapper accountTransferMapper;

    public PageResultBo<Account> list(PageQueryBo queryBo) {
        PageResultBo<Account> bo = PageResultBo.of(queryBo, accountMapper::getList);

        Map<Boolean, List<BigDecimal>> map = accountMapper.getListForSumMoney().stream().collect(Collectors.partitioningBy(Account::getDisabled, Collectors.mapping(Account::getBalance, Collectors.toList())));
        AccountSumMoney accountSumMoney = new AccountSumMoney();
        List<BigDecimal> disabledList = map.get(true);
        accountSumMoney.setCountDisabled(disabledList.size());
        accountSumMoney.setSumDisabled(disabledList.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        List<BigDecimal> enabledList = map.get(false);
        accountSumMoney.setCountEnabled(enabledList.size());
        accountSumMoney.setSumEnabled(enabledList.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        bo.setExtraInfo(accountSumMoney);

        return bo;
    }

    @Override
    protected void preSave(Account toBeSaved) {
        toBeSaved.setBalance(BigDecimal.ZERO);
        if (toBeSaved.getDisabled() == null) {
            toBeSaved.setDisabled(false);
        }
    }

    public void delete(Long id) {
        if (!exists(id)) {
            throw new DataNotFoundException("账户不存在");
        }
        if (accountMapper.hasBeenUsed(id)) {
            throw new WrongOperationException("无法删除已被使用的账户");
        }
        accountMapper.deleteByPrimaryKey(id);
    }

    public void update(Long id, Account account) {
        if (!exists(id)) {
            throw new DataNotFoundException("账户不存在");
        }
        account.setId(id);
        account.setBalance(null);
        account.setCreateId(null);
        account.setCreateTime(null);
        updateInternal(account);
    }

    public List<IdAndNameOfAccount> inOptions() {
        List<Account> list = accountMapper.getList(PageQueryBo.ALL);
        return list.stream().map(account -> {
            IdAndNameOfAccount n = new IdAndNameOfAccount();
            n.setId(account.getId());
            n.setName(account.getName());
            n.setBalance(account.getBalance());
            return n;
        }).collect(Collectors.toList());
    }

    public void transfer(AccountTransfer bo) {
        if (bo.getInAccountId().equals(bo.getOutAccountId())) {
            throw new DataInvalidException("转出账户和转入账户不能相同");
        }
        if (!exists(bo.getInAccountId())) {
            throw new DataInvalidException("转入账户不存在");
        }
        if (!exists(bo.getOutAccountId())) {
            throw new DataInvalidException("转出账户不存在");
        }
        bo.setCreateId(CurrentUser.getUserId());
        bo.setCreateTime(new Date());
        accountTransferMapper.insertSelective(bo);

        Journal journal = new Journal();
        journal.setStatus(JournalStatus.DONE_REVENUE);
        journal.setType(Constants.TYPE_OF_ACCOUNT_TRANSFER);
        journal.setAccountId(bo.getInAccountId());
        journal.setActualMoney(bo.getMoney());
        journal.setHandleDate(bo.getHandleDate());
        journal.setRemark(bo.getRemark());
        journal.setAttachments(bo.getAttachments());
        journal.setTransferId(bo.getId());
        journalService.save(journal);

        journal.setId(null);
        journal.setStatus(JournalStatus.DONE_EXPENSE);
        journal.setAccountId(bo.getOutAccountId());
        journalService.save(journal);
    }

    public AccountTransfer getAccountTransfer(Long transferId) {
        return accountTransferMapper.getDetail(transferId);
    }

    public void adjust(AccountAdjust bo) {
        if (journalService.existsAdjustRecord(bo.getAccountId())) {
            throw new WrongOperationException("该账户已经进行过期初调整");
        }
        Journal journal = new Journal();
        journal.setStatus(JournalStatus.DONE_REVENUE);
        journal.setType(Constants.TYPE_OF_ADJUST);
        journal.setAccountId(bo.getAccountId());
        journal.setActualMoney(bo.getBalance());
        journal.setHandleDate(bo.getHandleDate());
        journal.setRemark(bo.getRemark());
        journal.setAttachments(bo.getAttachments());
        journalService.save(journal);
    }
}
