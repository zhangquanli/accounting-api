package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.Role;
import com.github.zhangquanli.accounting.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/selectTree")
    public List<Role> selectTree() {
        return roleService.selectTree();
    }

    @PostMapping
    public void insert(@Valid @RequestBody Role role) {
        role.setId(null);
        roleService.insert(role);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Integer id, @Valid @RequestBody Role role) {
        role.setId(id);
        roleService.update(role);
    }
}
