package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Account;
import com.github.zhangquanli.accounting.query.AccountQuery;
import com.github.zhangquanli.accounting.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 账簿管理
 *
 * @author zhangquanli
 * @since 2021/12/22 10:32:00
 */
@RequestMapping("/accounts")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> selectList(AccountQuery accountQuery) {
        return accountService.selectList(accountQuery);
    }

    @PostMapping
    public void insert(@RequestBody Account account) {
        account.setId(null);
        accountService.insert(account);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody Account account) {
        account.setId(id);
        accountService.update(account);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        accountService.delete(id);
    }

    @GetMapping("/{id}")
    public Account selectOne(@PathVariable Integer id) {
        return accountService.selectOne(id);
    }
}
