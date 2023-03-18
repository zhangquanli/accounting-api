package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.query.ApiInfoQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.service.ApiInfoService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/selectList")
    public List<ApiInfo> selectList() {
        return apiInfoService.selectList();
    }

    @GetMapping("/selectPage")
    public Page<ApiInfo> selectPage(ApiInfoQuery apiInfoQuery, @Valid PageableQuery pageableQuery) {
        return apiInfoService.selectPage(apiInfoQuery, pageableQuery);
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
