package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.*;
import com.github.zhangquanli.accounting.query.VoucherQuery;
import com.github.zhangquanli.accounting.repository.LabelRepository;
import com.github.zhangquanli.accounting.repository.SubjectBalanceRepository;
import com.github.zhangquanli.accounting.repository.VoucherRepository;
import com.github.zhangquanli.accounting.service.VoucherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private LabelRepository labelRepository;
    private VoucherRepository voucherRepository;
    private SubjectBalanceRepository subjectBalanceRepository;

    @Autowired
    public void setLabelRepository(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Autowired
    public void setVoucherRepository(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Autowired
    public void setSubjectBalanceRepository(SubjectBalanceRepository subjectBalanceRepository) {
        this.subjectBalanceRepository = subjectBalanceRepository;
    }

    @Override
    public Page<Voucher> select(VoucherQuery voucherQuery) {
        int page = voucherQuery.getPage() - 1;
        int size = voucherQuery.getSize();
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

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void insert(Voucher voucher) {
        Integer accountId = voucher.getAccount().getId();

        // 会计分录
        for (AccountingEntry accountingEntry : voucher.getAccountingEntries()) {
            accountingEntry.setVoucher(voucher);
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
            if (accountingEntry.getLabels() == null
                    || accountingEntry.getLabels().isEmpty()) {
                continue;
            }
            List<Label> labels = accountingEntry.getLabels().stream()
                    .map(label -> labelRepository.findByName(label.getName()).orElse(label))
                    .collect(Collectors.toList());
            accountingEntry.setLabels(labels);
        }
        // 凭证
        voucher.setId(null);
        voucher.setCreateTime(LocalDateTime.now());
        voucherRepository.save(voucher);
    }

    @Transactional(rollbackFor = RuntimeException.class)
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
            List<Label> newLabels = accountingEntry.getLabels().stream()
                    .map(label -> labelRepository.findByName(label.getName()).orElse(label))
                    .collect(Collectors.toList());
            invalidAccountingEntry.setLabels(newLabels);
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
