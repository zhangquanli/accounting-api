package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.query.PageQuery;
import com.github.zhangquanli.accounting.query.VoucherQuery;
import com.github.zhangquanli.accounting.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 凭证管理
 *
 * @author zhangquanli
 * @since 2021/12/20 13:22:00
 */
@RequestMapping("/vouchers")
@RestController
public class VoucherController {

    private VoucherService voucherService;

    @Autowired
    public void setVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public Page<Voucher> select(VoucherQuery voucherQuery, @Valid PageQuery pageQuery) {
        return voucherService.select(voucherQuery, pageQuery);
    }

    @PostMapping
    public void insert(@Validated @RequestBody Voucher voucher) {
        voucherService.insert(voucher);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        voucherService.delete(id);
    }

    @GetMapping("/{id}")
    public Voucher selectOne(@PathVariable Integer id) {
        return voucherService.selectOne(id);
    }

}
