package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.query.AccountingEntryQuery;
import org.springframework.data.domain.Page;

/**
 * 描述
 *
 * @author zhangquanli
 * @since 2022/1/5 10:12:00
 */
public interface AccountingEntryService {
    Page<AccountingEntry> select(AccountingEntryQuery accountingEntryQuery);
}
