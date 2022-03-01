package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.query.AccountingEntryQuery;
import com.github.zhangquanli.accounting.service.AccountingEntryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author zhangquanli
 * @since 2022/1/5 10:13:00
 */
@Service
public class AccountingEntryServiceImpl implements AccountingEntryService {
    @Override
    public Page<AccountingEntry> select(AccountingEntryQuery accountingEntryQuery) {
        
        return null;
    }
}
