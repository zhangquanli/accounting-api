package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Label;
import com.github.zhangquanli.accounting.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签管理
 *
 * @author zhangquanli
 * @since 2021/12/20 13:02:00
 */
@RequestMapping("/labels")
@RestController
public class LabelController {

    private LabelService labelService;

    @Autowired
    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public List<Label> select() {
        return labelService.select();
    }

}
