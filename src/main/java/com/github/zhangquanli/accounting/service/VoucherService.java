package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.VoucherQuery;
import org.springframework.data.domain.Page;

/**
 * 凭证服务类
 */
public interface VoucherService {
    Page<Voucher> selectPage(VoucherQuery voucherQuery, PageableQuery pageableQuery);

    void insert(Voucher voucher);

    void delete(Integer id);

    Voucher selectOne(Integer id);
}
