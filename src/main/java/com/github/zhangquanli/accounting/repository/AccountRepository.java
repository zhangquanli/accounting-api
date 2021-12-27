package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 账簿仓库类
 *
 * @author zhangquanli
 * @since 2021/12/22 10:33:00
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
