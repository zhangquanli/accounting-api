package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.entity.base.PageInfo;
import com.github.zhangquanli.accounting.service.PageInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.github.zhangquanli.accounting.entity.base.PageInfo.Type.VIRTUALITY;

/**
 * 页面管理
 *
 * @author zhangquanli
 * @since 2023/2/28
 */
@RequestMapping("/pageInfos")
@RestController
public class PageInfoController {
    private final PageInfoService pageInfoService;

    public PageInfoController(PageInfoService pageInfoService) {
        this.pageInfoService = pageInfoService;
    }

    @GetMapping
    public List<PageInfo> selectList() {
        return pageInfoService.selectList();
    }

    @GetMapping("/apiInfos")
    public List<ApiInfo> selectApis() {
        return pageInfoService.selectApis();
    }

    @PostMapping
    public void insert(@Validated @RequestBody PageInfo pageInfo) {
        pageInfo.setId(null);
        validate(pageInfo);
        pageInfoService.insert(pageInfo);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Validated @RequestBody PageInfo pageInfo) {
        pageInfo.setId(id);
        validate(pageInfo);
        pageInfoService.update(pageInfo);
    }

    private void validate(PageInfo pageInfo) {
        pageInfo.setChildren(Collections.emptyList());
        if (pageInfo.getType() == VIRTUALITY) {
            pageInfo.setApiInfos(Collections.emptyList());
            pageInfo.setComponentInfos(Collections.emptyList());
        }
    }
}
