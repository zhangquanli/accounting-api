package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.PageInfo;
import com.github.zhangquanli.accounting.service.PageInfoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static com.github.zhangquanli.accounting.entity.base.PageInfo.Type.VIRTUALITY;

/**
 * 页面管理
 */
@RequestMapping("/pageInfos")
@RestController
public class PageInfoController {
    private final PageInfoService pageInfoService;

    public PageInfoController(PageInfoService pageInfoService) {
        this.pageInfoService = pageInfoService;
    }

    @GetMapping
    public ListResult<PageInfo> selectAll() {
        return pageInfoService.selectAll();
    }

    @GetMapping("/{id}")
    public PageInfo selectOne(@PathVariable Integer id) {
        return pageInfoService.selectOne(id);
    }

    @PostMapping
    public void insert(@Valid @RequestBody PageInfo pageInfo) {
        pageInfo.setId(null);
        validate(pageInfo);
        pageInfoService.insert(pageInfo);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Valid @RequestBody PageInfo pageInfo) {
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
