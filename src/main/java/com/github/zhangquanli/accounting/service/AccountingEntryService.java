package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.query.AccountingEntryQuery;
import com.github.zhangquanli.accounting.query.PageQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 描述
 *
 * @author zhangquanli
 * @since 2022/1/5 10:12:00
 */
public interface AccountingEntryService {
    Page<AccountingEntry> selectPage(AccountingEntryQuery accountingEntryQuery, PageQuery pageQuery);

    List<AccountingEntry> selectList(AccountingEntryQuery accountingEntryQuery);
}
