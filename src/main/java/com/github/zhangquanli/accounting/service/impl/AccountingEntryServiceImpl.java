package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.AccountingEntry;
import com.github.zhangquanli.accounting.query.AccountingEntryQuery;
import com.github.zhangquanli.accounting.repository.AccountingEntryRepository;
import com.github.zhangquanli.accounting.service.AccountingEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    private AccountingEntryRepository accountingEntryRepository;

    @Autowired
    public void setAccountingEntryRepository(AccountingEntryRepository accountingEntryRepository) {
        this.accountingEntryRepository = accountingEntryRepository;
    }

    @Override
    public Page<AccountingEntry> select(AccountingEntryQuery accountingEntryQuery) {
        int page = accountingEntryQuery.getPage() - 1;
        int size = accountingEntryQuery.getSize();
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<AccountingEntry> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 账簿
            if (accountingEntryQuery.getAccountId() != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("subjectBalance").get("account").get("id"),
                        accountingEntryQuery.getAccountId());
                predicates.add(predicate);
            }
            // 科目集合
            if (accountingEntryQuery.getSubjectIds() != null &&
                    !accountingEntryQuery.getSubjectIds().isEmpty()) {
                Predicate predicate = root.get("subjectBalance").get("subject").get("id")
                        .in(accountingEntryQuery.getSubjectIds());
                predicates.add(predicate);
            }
            // 摘要
            if (accountingEntryQuery.getSummary() != null &&
                    !accountingEntryQuery.getSummary().trim().equals("")) {
                Predicate predicate = criteriaBuilder.like(root.get("summary"),
                        accountingEntryQuery.getSummary() + "%");
                predicates.add(predicate);
            }
            // 标签集合
            if (accountingEntryQuery.getLabelIds() != null &&
                    !accountingEntryQuery.getLabelIds().isEmpty()) {
                Predicate predicate = root.get("labels").get("id")
                        .in(accountingEntryQuery.getLabelIds());
                predicates.add(predicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return accountingEntryRepository.findAll(specification, pageable);
    }
}
