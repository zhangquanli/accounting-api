package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Account;
import com.github.zhangquanli.accounting.query.AccountQuery;

import java.util.List;

/**
 * 账簿服务类
 */
public interface AccountService {
    List<Account> selectList(AccountQuery accountQuery);

    void insert(Account account);

    void update(Account account);

    void delete(Integer id);

    Account selectOne(Integer id);
}
