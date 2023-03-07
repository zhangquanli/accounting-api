package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.Page;

import java.util.List;

public interface PageService {
    List<Page> selectList();

    void insert(Page page);

    void update(Page page);
}
