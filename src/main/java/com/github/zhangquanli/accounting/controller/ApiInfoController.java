package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.query.ApiInfoQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.service.ApiInfoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 接口管理
 *
 * @author zhangquanli
 * @since 2023/3/2
 */
@RequestMapping("/apiInfos")
@RestController
public class ApiInfoController {
    private final ApiInfoService apiInfoService;

    public ApiInfoController(ApiInfoService apiInfoService) {
        this.apiInfoService = apiInfoService;
    }

    @GetMapping
    public ListResult<ApiInfo> selectAll(ApiInfoQuery apiInfoQuery, PageableQuery pageableQuery) {
        return apiInfoService.selectAll(apiInfoQuery, pageableQuery);
    }

    @GetMapping("/{id}")
    public ApiInfo selectOne(@PathVariable Integer id) {
        return apiInfoService.selectOne(id);
    }

    @PostMapping
    public void insert(@Valid @RequestBody ApiInfo apiInfo) {
        apiInfo.setId(null);
        apiInfoService.insert(apiInfo);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Valid @RequestBody ApiInfo apiInfo) {
        apiInfo.setId(id);
        apiInfoService.update(apiInfo);
    }
}
