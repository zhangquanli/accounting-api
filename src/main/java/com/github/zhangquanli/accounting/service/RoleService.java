package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.Role;

import java.util.List;

public interface RoleService {
    List<Role> selectList();

    void update(Role role);
}
