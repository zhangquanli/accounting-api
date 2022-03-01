package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.query.VoucherQuery;
import org.springframework.data.domain.Page;

/**
 * 凭证服务类
 *
 * @author zhangquanli
 * @since 2021/12/21 16:41:00
 */
public interface VoucherService {
    Page<Voucher> select(VoucherQuery voucherReq);

    Voucher get(Integer id);

    void insert(Voucher voucher);
}
