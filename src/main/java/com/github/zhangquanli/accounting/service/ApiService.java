package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.Api;
import com.github.zhangquanli.accounting.query.ApiQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import org.springframework.data.domain.Page;

public interface ApiService {
    Page<Api> selectPage(ApiQuery apiQuery, PageableQuery pageableQuery);

    void insert(Api api);

    void update(Api api);
}
