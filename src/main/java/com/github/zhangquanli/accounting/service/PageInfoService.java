package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.PageInfo;

public interface PageInfoService {
    ListResult<PageInfo> selectAll();

    PageInfo selectOne(Integer id);

    void insert(PageInfo pageInfo);

    void update(PageInfo pageInfo);
}
