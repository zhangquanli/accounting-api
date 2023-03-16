package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.UserQuery;
import com.github.zhangquanli.accounting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色管理
 *
 * @author zhangquanli
 * @since 2023/3/14
 */
@RequestMapping("/users")
@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> selectPage(UserQuery userQuery, @Valid PageableQuery pageableQuery) {
        return userService.selectPage(userQuery, pageableQuery);
    }

    @PostMapping
    public void insert(@RequestBody User user) {
        user.setId(null);
        userService.insert(user);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        userService.update(user);
    }
}
