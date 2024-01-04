package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 账簿仓库类
 */
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
}
