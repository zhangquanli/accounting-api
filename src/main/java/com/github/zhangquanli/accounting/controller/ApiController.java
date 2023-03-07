package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.Api;
import com.github.zhangquanli.accounting.query.ApiQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.service.ApiService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 接口管理
 *
 * @author zhangquanli
 * @since 2023/3/2
 */
@RequestMapping("/apis")
@RestController
public class ApiController {
    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public Page<Api> selectPage(ApiQuery apiQuery, @Valid PageableQuery pageableQuery) {
        return apiService.selectPage(apiQuery, pageableQuery);
    }

    @PostMapping
    public void insert(@Valid @RequestBody Api api) {
        api.setId(null);
        apiService.insert(api);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Valid @RequestBody Api api) {
        api.setId(id);
        apiService.update(api);
    }
}
