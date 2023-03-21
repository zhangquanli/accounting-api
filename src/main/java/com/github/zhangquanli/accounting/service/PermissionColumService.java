package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.PermissionColumn;

import java.util.List;

public interface PermissionColumService {
    List<PermissionColumn> selectList();

    PermissionColumn selectOne(Integer id);
}
