package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.*;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.VoucherQuery;
import com.github.zhangquanli.accounting.repository.LabelRepository;
import com.github.zhangquanli.accounting.repository.SubjectBalanceRepository;
import com.github.zhangquanli.accounting.repository.VoucherRepository;
import com.github.zhangquanli.accounting.service.VoucherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 凭证服务类
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class VoucherServiceImpl implements VoucherService {
    private final LabelRepository labelRepository;
    private final VoucherRepository voucherRepository;
    private final SubjectBalanceRepository subjectBalanceRepository;

    public VoucherServiceImpl(LabelRepository labelRepository, VoucherRepository voucherRepository, SubjectBalanceRepository subjectBalanceRepository) {
        this.labelRepository = labelRepository;
        this.voucherRepository = voucherRepository;
        this.subjectBalanceRepository = subjectBalanceRepository;
    }

    @Override
    public Page<Voucher> selectPage(VoucherQuery voucherQuery, PageableQuery pageableQuery) {
        int page = pageableQuery.getPage() - 1;
        int size = pageableQuery.getSize();
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Voucher> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 编号
            if (voucherQuery.getNum() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("num"),
                        "%" + voucherQuery.getNum() + "%");
                predicates.add(predicate);
            }
            // 记账日期
            if (voucherQuery.getStartAccountDate() != null) {
                Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("accountDate"),
                        voucherQuery.getStartAccountDate());
                predicates.add(predicate);
            }
            if (voucherQuery.getEndAccountDate() != null) {
                Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("accountDate"),
                        voucherQuery.getEndAccountDate());
                predicates.add(predicate);
            }
            // 账簿
            if (voucherQuery.getAccountId() != null) {
                Account account = new Account();
                account.setId(voucherQuery.getAccountId());
                Predicate predicate = criteriaBuilder.equal(root.get("account"), account);
                predicates.add(predicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return voucherRepository.findAll(specification, pageable);
    }

    @Override
    public void insert(Voucher voucher) {
        Integer accountId = voucher.getAccount().getId();
        // 会计分录
        for (AccountingEntry accountingEntry : voucher.getAccountingEntries()) {
            accountingEntry.setVoucher(voucher);
            accountingEntry.setCreateTime(LocalDateTime.now());
            // 科目余额
            Integer subjectId = accountingEntry.getSubjectBalance().getSubject().getId();
            SubjectBalance subjectBalance = subjectBalanceRepository.findBySubject_IdAndAccount_Id(subjectId, accountId)
                    .orElseThrow(EntityNotFoundException::new);
            BigDecimal symbol = subjectBalance.getSubject().getCategory().symbol(accountingEntry.getType());
            BigDecimal currentAmount = accountingEntry.getAmount().multiply(symbol)
                    .add(subjectBalance.getCurrentAmount());
            subjectBalance.setCurrentAmount(currentAmount);
            accountingEntry.setBalance(currentAmount);
            accountingEntry.setSubjectBalance(subjectBalance);
            // 标签集合
            if (accountingEntry.getLabels() == null || accountingEntry.getLabels().isEmpty()) {
                continue;
            }
            List<Label> labels = getLabels(accountingEntry);
            accountingEntry.setLabels(labels);
        }
        // 凭证
        voucher.setId(null);
        voucher.setCreateTime(LocalDateTime.now());
        voucherRepository.save(voucher);
    }

    private List<Label> getLabels(AccountingEntry accountingEntry) {
        return accountingEntry.getLabels().stream()
                .map(label -> labelRepository.findByMark(label.getMark()).orElseGet(() -> {
                    String[] split = label.getMark().split("-");
                    if (split.length != 2) {
                        throw new RuntimeException("录入的标签数据异常");
                    }
                    label.setName(split[0]);
                    label.setContent(split[1]);
                    return label;
                }))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        Voucher originalVoucher = voucherRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        // 冲红凭证
        Voucher invalidVoucher = new Voucher();
        BeanUtils.copyProperties(originalVoucher, invalidVoucher);
        invalidVoucher.setId(null);
        invalidVoucher.setNum(originalVoucher.getNum() + ":invalid");
        invalidVoucher.setCreateTime(LocalDateTime.now());
        List<AccountingEntry> invalidAccountingEntries = new ArrayList<>();
        for (AccountingEntry accountingEntry : originalVoucher.getAccountingEntries()) {
            AccountingEntry invalidAccountingEntry = new AccountingEntry();
            BeanUtils.copyProperties(accountingEntry, invalidAccountingEntry);
            invalidAccountingEntry.setId(null);
            SubjectBalance subjectBalance = accountingEntry.getSubjectBalance();
            // 会计分录的金额和余额
            BigDecimal symbol = subjectBalance.getSubject().getCategory().symbol(accountingEntry.getType());
            BigDecimal amount = accountingEntry.getAmount().negate();
            BigDecimal balance = subjectBalance.getCurrentAmount().add(amount.multiply(symbol));
            invalidAccountingEntry.setAmount(amount);
            invalidAccountingEntry.setBalance(balance);
            // 科目余额
            subjectBalance.setCurrentAmount(balance);
            // 所属凭证
            invalidAccountingEntry.setVoucher(invalidVoucher);
            // 标签集合
            List<Label> labels = new ArrayList<>(accountingEntry.getLabels());
            invalidAccountingEntry.setLabels(labels);
            // 关联的原始会计分录
            invalidAccountingEntry.setOriginalAccountingEntry(accountingEntry);
            // 关联的冲红会计分录
            accountingEntry.setInvalidAccountingEntry(invalidAccountingEntry);
            invalidAccountingEntries.add(invalidAccountingEntry);
        }
        invalidVoucher.setAccountingEntries(invalidAccountingEntries);
        invalidVoucher.setOriginalVoucher(originalVoucher);
        voucherRepository.save(invalidVoucher);
        // 原始凭证
        originalVoucher.setInvalidVoucher(invalidVoucher);
        voucherRepository.save(originalVoucher);
    }

    @Override
    public Voucher selectOne(Integer id) {
        return voucherRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
