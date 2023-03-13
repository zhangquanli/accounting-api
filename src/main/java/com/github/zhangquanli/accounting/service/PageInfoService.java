package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.entity.base.PageInfo;

import java.util.List;

public interface PageInfoService {
    List<PageInfo> selectList();

    List<ApiInfo> selectApis();

    void insert(PageInfo pageInfo);

    void update(PageInfo pageInfo);
}
