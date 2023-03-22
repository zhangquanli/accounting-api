package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.Role;

public interface RoleService {
    ListResult<Role> selectAll();

    Role selectOne(Integer id);

    void insert(Role role);

    void update(Role role);
}
