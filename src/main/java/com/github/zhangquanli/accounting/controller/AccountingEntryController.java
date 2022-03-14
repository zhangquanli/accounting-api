package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.query.AccountingEntryQuery;
import com.github.zhangquanli.accounting.service.AccountingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 会计分录管理
 *
 * @author zhangquanli
 * @since 2022/1/5 10:05:00
 */
@RequestMapping("/accountingEntries")
@RestController
public class AccountingEntryController {

    private AccountingEntryService accountingEntryService;

    @Autowired
    public void setAccountingEntryService(AccountingEntryService accountingEntryService) {
        this.accountingEntryService = accountingEntryService;
    }

    @GetMapping
    public Page<AccountingEntry> select(@RequestBody AccountingEntryQuery accountingEntryQuery) {
        return accountingEntryService.select(accountingEntryQuery);
    }
}
