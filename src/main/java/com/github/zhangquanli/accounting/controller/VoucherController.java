package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public void insert(@RequestBody Voucher voucher) {
        voucherService.insert(voucher);
    }
}
