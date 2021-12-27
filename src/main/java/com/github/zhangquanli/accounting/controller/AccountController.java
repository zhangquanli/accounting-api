package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Account;
import com.github.zhangquanli.accounting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 账簿管理
 *
 * @author zhangquanli
 * @since 2021/12/22 10:32:00
 */
@CrossOrigin
@RequestMapping("/accounts")
@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> select() {
        return accountService.select();
    }

    @PostMapping
    public void insert( @RequestBody Account account) {
        accountService.insert(account);
    }

}
