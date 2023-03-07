package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.query.AccountingEntryQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.repository.AccountingEntryRepository;
import com.github.zhangquanli.accounting.service.AccountingEntryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author zhangquanli
 * @since 2022/1/5 10:13:00
 */
@Service
public class AccountingEntryServiceImpl implements AccountingEntryService {

    private final AccountingEntryRepository accountingEntryRepository;

    public AccountingEntryServiceImpl(AccountingEntryRepository accountingEntryRepository) {
        Assert.notNull(accountingEntryRepository, "accountingEntryRepository cannot be null");
        this.accountingEntryRepository = accountingEntryRepository;
    }

    @Override
    public Page<AccountingEntry> selectPage(AccountingEntryQuery accountingEntryQuery, PageableQuery pageableQuery) {
        int page = pageableQuery.getCurrent() - 1;
        int size = pageableQuery.getPageSize();
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<AccountingEntry> specification = toSpecification(accountingEntryQuery);
        return accountingEntryRepository.findAll(specification, pageable);
    }

    @Override
    public List<AccountingEntry> selectList(AccountingEntryQuery accountingEntryQuery) {
        Specification<AccountingEntry> specification = toSpecification(accountingEntryQuery);
        return accountingEntryRepository.findAll(specification);
    }

    private Specification<AccountingEntry> toSpecification(AccountingEntryQuery accountingEntryQuery) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 凭证日期
            if (accountingEntryQuery.getStartVoucherDate() != null) {
                Predicate predicate = cb.greaterThanOrEqualTo(root.join("voucher").get("accountDate"),
                        accountingEntryQuery.getStartVoucherDate());
                predicates.add(predicate);
            }
            if (accountingEntryQuery.getEndVoucherDate() != null) {
                Predicate predicate = cb.lessThanOrEqualTo(root.join("voucher").get("accountDate"),
                        accountingEntryQuery.getEndVoucherDate());
                predicates.add(predicate);
            }
            // 账簿
            if (accountingEntryQuery.getAccountId() != null) {
                Predicate predicate = cb.equal(root.join("subjectBalance").join("account").get("id"),
                        accountingEntryQuery.getAccountId());
                predicates.add(predicate);
            }
            // 科目
            if (accountingEntryQuery.getSubjectId() != null) {
                Predicate predicate = cb.equal(root.join("subjectBalance").join("subject").get("id"),
                        accountingEntryQuery.getSubjectId());
                predicates.add(predicate);
            }
            // 摘要
            if (accountingEntryQuery.getSummary() != null &&
                    !accountingEntryQuery.getSummary().trim().equals("")) {
                Predicate predicate = cb.like(root.get("summary"),
                        accountingEntryQuery.getSummary() + "%");
                predicates.add(predicate);
            }
            // 标签
            if (accountingEntryQuery.getLabelId() != null) {
                Predicate predicate = cb.equal(root.join("labels").get("id"),
                        accountingEntryQuery.getLabelId());
                predicates.add(predicate);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
