package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.UserQuery;
import org.springframework.data.domain.Page;

/**
 * 用户管理
 *
 * @author zhangquanli
 * @since 2023/3/14
 */
public interface UserService {
    Page<User> selectPage(UserQuery userQuery, PageableQuery pageableQuery);

    User selectByUsername(String username);

    void insert(User user);

    void update(User user);
}
