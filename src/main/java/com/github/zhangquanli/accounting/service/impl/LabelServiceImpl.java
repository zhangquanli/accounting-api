package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.Label;
import com.github.zhangquanli.accounting.repository.LabelRepository;
import com.github.zhangquanli.accounting.service.LabelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签服务类
 *
 * @author zhangquanli
 * @since 2021/12/20 13:08:00
 */
@Service
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;

    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public List<Label> selectList() {
        return labelRepository.findAll();
    }
}
