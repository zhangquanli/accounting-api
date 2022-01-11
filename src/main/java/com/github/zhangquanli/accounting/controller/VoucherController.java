package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.req.VoucherReq;
import com.github.zhangquanli.accounting.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 凭证管理
 *
 * @author zhangquanli
 * @since 2021/12/20 13:22:00
 */
@CrossOrigin
@RequestMapping("/vouchers")
@RestController
public class VoucherController {

    private VoucherService voucherService;

    @Autowired
    public void setVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public Page<Voucher> select(VoucherReq voucherReq) {
        return voucherService.select(voucherReq);
    }

    @GetMapping("/{id}")
    public Voucher get(@PathVariable Integer id) {
        return voucherService.get(id);
    }

    @PostMapping
    public void insert(@Validated @RequestBody Voucher voucher) {
        voucherService.insert(voucher);
    }

    
}
