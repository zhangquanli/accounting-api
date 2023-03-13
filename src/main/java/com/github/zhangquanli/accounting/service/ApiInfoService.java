package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.query.ApiInfoQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import org.springframework.data.domain.Page;

public interface ApiInfoService {
    Page<ApiInfo> selectPage(ApiInfoQuery apiInfoQuery, PageableQuery pageableQuery);

    void insert(ApiInfo apiInfo);

    void update(ApiInfo apiInfo);
}
