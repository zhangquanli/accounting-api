package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.Role;
import com.github.zhangquanli.accounting.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author zhangquanli
 * @since 2023/2/28
 */
@RequestMapping("/roles")
@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> selectList() {
        return roleService.selectList();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Integer id, @RequestBody Role role) {
        role.setId(id);
        roleService.update(role);
    }
}
