package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.Account;
import com.github.zhangquanli.accounting.query.AccountQuery;
import com.github.zhangquanli.accounting.repository.AccountRepository;
import com.github.zhangquanli.accounting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 账簿服务类
 *
 * @author zhangquanli
 * @since 2021/12/22 10:40:00
 */
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> select(AccountQuery accountQuery) {
        Specification<Account> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (accountQuery.getName() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("name"),
                        "%" + accountQuery.getName() + "%");
                predicates.add(predicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return accountRepository.findAll(specification, sort);
    }

    @Override
    public void insert(Account account) {
        account.setId(null);
        accountRepository.save(account);
    }

    @Override
    public void update(Integer id, Account account) {
        account.setId(id);
        accountRepository.save(account);
    }

}
