package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.entity.Label;
import com.github.zhangquanli.accounting.entity.SubjectBalance;
import com.github.zhangquanli.accounting.entity.Voucher;
import com.github.zhangquanli.accounting.query.VoucherQuery;
import com.github.zhangquanli.accounting.repository.LabelRepository;
import com.github.zhangquanli.accounting.repository.SubjectBalanceRepository;
import com.github.zhangquanli.accounting.repository.VoucherRepository;
import com.github.zhangquanli.accounting.service.VoucherService;
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
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return voucherRepository.findAll(specification, pageable);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void insert(Voucher voucher) {
        // 会计分录
        for (AccountingEntry accountingEntry : voucher.getAccountingEntries()) {
            accountingEntry.setVoucher(voucher);
            // 科目余额
            Integer subjectBalanceId = accountingEntry.getSubjectBalance().getId();
            SubjectBalance subjectBalance = subjectBalanceRepository.findById(subjectBalanceId)
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

    @Override
    public Voucher selectOne(Integer id) {
        return voucherRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
