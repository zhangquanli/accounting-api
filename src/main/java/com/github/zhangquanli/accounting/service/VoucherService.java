package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.query.PageQuery;
import com.github.zhangquanli.accounting.query.VoucherQuery;
import org.springframework.data.domain.Page;

/**
 * 凭证服务类
 *
 * @author zhangquanli
 * @since 2021/12/21 16:41:00
 */
public interface VoucherService {
    Page<Voucher> select(VoucherQuery voucherQuery, PageQuery pageQuery);

    void insert(Voucher voucher);

    void delete(Integer id);

    Voucher selectOne(Integer id);
}
