package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Label;

import java.util.List;

/**
 * 标签服务类
 *
 * @author zhangquanli
 * @since 2021/12/20 13:08:00
 */
public interface LabelService {
    List<Label> selectList();
}
