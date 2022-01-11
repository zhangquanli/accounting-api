package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.Account;
import com.github.zhangquanli.accounting.entity.SubjectBalance;
import com.github.zhangquanli.accounting.repository.AccountRepository;
import com.github.zhangquanli.accounting.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<Account> select() {
        return accountRepository.findAll();
    }

    @Override
    public void insert(Account account) {
        account.setId(null);
        for (SubjectBalance balance : account.getSubjectBalances()) {
            balance.setInitialAmount(BigDecimal.ZERO);
            balance.setCurrentAmount(BigDecimal.ZERO);
            balance.setAccount(account);
        }
        accountRepository.save(account);
    }

}
