package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.Role;

import java.util.List;

public interface RoleService {
    List<Role> selectTree();

    void insert(Role role);

    void update(Role role);
}
