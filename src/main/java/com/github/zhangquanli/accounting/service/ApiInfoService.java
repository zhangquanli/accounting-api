package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.query.ApiInfoQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;

public interface ApiInfoService {
    ListResult<ApiInfo> selectAll(ApiInfoQuery apiInfoQuery, PageableQuery pageableQuery);

    ApiInfo selectOne(Integer id);

    void insert(ApiInfo apiInfo);

    void update(ApiInfo apiInfo);
}
