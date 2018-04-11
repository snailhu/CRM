package com.zifangdt.ch.finance.web;

import com.zifangdt.ch.base.bo.IdAndNameOfAccount;
import com.zifangdt.ch.base.bo.PageQueryBo;
import com.zifangdt.ch.base.bo.PageResultBo;
import com.zifangdt.ch.base.dto.finance.Account;
import com.zifangdt.ch.base.dto.finance.AccountTransfer;
import com.zifangdt.ch.finance.bo.AccountAdjust;
import com.zifangdt.ch.finance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by 袁兵 on 2018/4/2.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public Long save(@RequestBody @Valid Account account) {
        accountService.save(account);
        return account.getId();
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable("id") Long id) {
        return accountService.get(id);
    }

    @GetMapping
    public PageResultBo<Account> list(PageQueryBo queryBo) {
        return accountService.list(queryBo);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid Account account) {
        accountService.update(id, account);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        accountService.delete(id);
    }

    @GetMapping("/inOptions")
    public List<IdAndNameOfAccount> inOptions() {
        return accountService.inOptions();
    }

    @GetMapping("/transfers/{id}")
    public AccountTransfer accountTransfer(@PathVariable("id") Long id) {
        return accountService.getAccountTransfer(id);
    }

    @PostMapping("/transfers")
    public void transfer(@RequestBody @Valid AccountTransfer bo) {
        accountService.transfer(bo);
    }

    @PostMapping("/adjusts")
    public void adjust(@RequestBody @Valid AccountAdjust bo) {
        accountService.adjust(bo);
    }
}
