package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.UserQuery;

/**
 * 用户管理
 */
public interface UserService {
    ListResult<User> selectAll(UserQuery userQuery, PageableQuery pageableQuery);

    User selectOne(Integer id);

    User selectByUsername(String username);

    void insert(User user);

    void update(User user);
}
