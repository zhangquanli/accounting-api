package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.VoucherQuery;
import com.github.zhangquanli.accounting.service.VoucherService;
import org.springframework.data.domain.Page;
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
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public Page<Voucher> selectPage(VoucherQuery voucherQuery, @Valid PageableQuery pageableQuery) {
        return voucherService.selectPage(voucherQuery, pageableQuery);
    }

    @PostMapping
    public void insert(@Valid @RequestBody Voucher voucher) {
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
