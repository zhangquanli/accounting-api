package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 会计分录仓库类
 */
public interface AccountingEntryRepository extends JpaRepository<AccountingEntry, Integer>,
        JpaSpecificationExecutor<AccountingEntry> {
}
