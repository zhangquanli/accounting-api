package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.SubjectBalance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 会计余额仓库类
 *
 * @author zhangquanli
 * @since 2021/12/22 10:35:00
 */
public interface SubjectBalanceRepository extends JpaRepository<SubjectBalance, Integer> {
}
