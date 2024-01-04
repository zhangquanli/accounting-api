package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.Account;
import com.github.zhangquanli.accounting.entity.SubjectBalance;
import com.github.zhangquanli.accounting.query.AccountQuery;
import com.github.zhangquanli.accounting.repository.AccountRepository;
import com.github.zhangquanli.accounting.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> selectList(AccountQuery accountQuery) {
        Specification<Account> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (accountQuery.getName() != null) {
                Predicate predicate = builder.like(root.get("name"),
                        "%" + accountQuery.getName() + "%");
                predicates.add(predicate);
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return accountRepository.findAll(specification, sort);
    }

    @Override
    public void insert(Account account) {
        if (account.getSubjectBalances() != null) {
            for (SubjectBalance subjectBalance : account.getSubjectBalances()) {
                subjectBalance.setAccount(account);
            }
        }
        accountRepository.save(account);
    }

    @Override
    public void update(Account newAccount) {
        Account account = accountRepository.findById(newAccount.getId()).orElseThrow(EntityNotFoundException::new);
        // 修改会计科目数据
        List<SubjectBalance> subjectBalances = account.getSubjectBalances();
        List<Integer> subjectIds = subjectBalances.stream()
                .map(subjectBalance -> subjectBalance.getSubject().getId())
                .toList();
        if (newAccount.getSubjectBalances() != null) {
            for (SubjectBalance subjectBalance : newAccount.getSubjectBalances()) {
                if (subjectIds.contains(subjectBalance.getSubject().getId())) {
                    continue;
                }
                subjectBalance.setAccount(account);
                subjectBalances.add(subjectBalance);
            }
        }
        accountRepository.save(account);
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account selectOne(Integer id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
