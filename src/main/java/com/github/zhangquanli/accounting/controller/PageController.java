package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.Page;
import com.github.zhangquanli.accounting.service.PageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.github.zhangquanli.accounting.entity.base.Page.Type.REALITY;
import static com.github.zhangquanli.accounting.entity.base.Page.Type.VIRTUALITY;

/**
 * 页面管理
 *
 * @author zhangquanli
 * @since 2023/2/28
 */
@RequestMapping("/pages")
@RestController
public class PageController {
    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping
    public List<Page> selectList() {
        return pageService.selectList();
    }

    @PostMapping
    public void insert(@Validated @RequestBody Page page) {
        page.setId(null);
        validate(page);
        pageService.insert(page);
    }

    private void validate(Page page) {
        if (page.getType() == REALITY) {
            page.setChildren(Collections.emptySet());
        } else if (page.getType() == VIRTUALITY) {
            page.setApis(Collections.emptySet());
            page.setComponents(Collections.emptySet());
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Validated @RequestBody Page page) {
        page.setId(id);
        validate(page);
        pageService.update(page);
    }
}
