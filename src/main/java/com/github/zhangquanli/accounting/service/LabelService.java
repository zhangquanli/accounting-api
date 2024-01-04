package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Label;

import java.util.List;

/**
 * 标签服务类
 */
public interface LabelService {
    List<Label> selectList();
}
