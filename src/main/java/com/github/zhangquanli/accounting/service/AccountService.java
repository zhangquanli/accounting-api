package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Account;

import java.util.List;

/**
 * 账簿服务类
 *
 * @author zhangquanli
 * @since 2021/12/22 10:40:00
 */
public interface AccountService {
    List<Account> select();

    void insert(Account account);
}
