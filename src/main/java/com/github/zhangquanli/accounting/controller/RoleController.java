package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.Role;
import com.github.zhangquanli.accounting.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ListResult<Role> selectAll() {
        return roleService.selectAll();
    }

    @GetMapping("/{id}")
    public Role selectOne(@PathVariable Integer id) {
        return roleService.selectOne(id);
    }

    @PostMapping
    public void insert(@Valid @RequestBody Role role) {
        role.setId(null);
        roleService.insert(role);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Valid @RequestBody Role role) {
        role.setId(id);
        roleService.update(role);
    }
}
