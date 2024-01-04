package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 凭证仓库类
 */
public interface VoucherRepository extends JpaRepository<Voucher, Integer>, JpaSpecificationExecutor<Voucher> {
}
