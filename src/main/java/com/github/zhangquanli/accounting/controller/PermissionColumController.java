package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.OptionType;
import com.github.zhangquanli.accounting.entity.base.PermissionColumn;
import com.github.zhangquanli.accounting.entity.base.PermissionLevel;
import com.github.zhangquanli.accounting.service.PermissionColumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限字段
 *
 * @author zhangquanli
 * @since 2023/3/16
 */
@RequestMapping("/permissionColumns")
@RestController
public class PermissionColumController {
    private PermissionColumService permissionColumService;

    @Autowired
    public void setPermissionColumService(PermissionColumService permissionColumService) {
        this.permissionColumService = permissionColumService;
    }

    @GetMapping
    public List<PermissionColumn> selectList() {
        return permissionColumService.selectList();
    }

    @GetMapping("/{id}")
    public PermissionColumn selectOne(@PathVariable Integer id) {
        return permissionColumService.selectOne(id);
    }

    @GetMapping("/options")
    public List<OptionType> getOptions(PermissionLevel level, String parentCode) {
        return level.getOptions(parentCode);
    }
}
