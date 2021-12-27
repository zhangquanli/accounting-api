package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.entity.Label;
import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.repository.LabelRepository;
import com.github.zhangquanli.accounting.repository.VoucherRepository;
import com.github.zhangquanli.accounting.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 凭证服务类
 *
 * @author zhangquanli
 * @since 2021/12/21 16:41:00
 */
@Service
public class VoucherServiceImpl implements VoucherService {

    private VoucherRepository voucherRepository;
    private LabelRepository labelRepository;

    @Autowired
    public void setVoucherRepository(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Autowired
    public void setLabelRepository(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void insert(Voucher voucher) {
        // 处理标签数据
        if (voucher.getAccountingEntries() != null && !voucher.getAccountingEntries().isEmpty()) {
            for (AccountingEntry accountingEntry : voucher.getAccountingEntries()) {
                if (accountingEntry.getLabels() == null || accountingEntry.getLabels().isEmpty()) {
                    continue;
                }
                List<Label> labels = accountingEntry.getLabels().stream()
                        .map(label -> {
                            Label newLabel = labelRepository.findByName(label.getName());
                            if (newLabel == null) {
                                return labelRepository.save(label);
                            }
                            return newLabel;
                        }).collect(Collectors.toList());
                accountingEntry.setLabels(labels);
            }
        }
        // 处理凭证数据
        voucher.setId(null);
        voucher.setCreateTime(LocalDateTime.now());
        voucher.getAccountingEntries().forEach(accountingEntry -> accountingEntry.setVoucher(voucher));
        voucherRepository.save(voucher);
    }
}
