package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 会计分录仓库类
 *
 * @author zhangquanli
 * @since 2021/12/22 10:34:00
 */
public interface AccountingEntryRepository extends JpaRepository<AccountingEntry, Integer> {
}
