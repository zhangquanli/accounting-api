package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.PageInfo;
import com.github.zhangquanli.accounting.service.PageInfoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/selectTree")
    public List<PageInfo> selectTree() {
        return pageInfoService.selectTree();
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
