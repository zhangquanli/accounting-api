package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.query.AccountingEntryQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountingEntryService {
    Page<AccountingEntry> selectPage(AccountingEntryQuery accountingEntryQuery, PageableQuery pageableQuery);

    List<AccountingEntry> selectList(AccountingEntryQuery accountingEntryQuery);
}
