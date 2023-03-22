package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.UserQuery;
import com.github.zhangquanli.accounting.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户管理
 *
 * @author zhangquanli
 * @since 2023/3/14
 */
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ListResult<User> selectAll(UserQuery userQuery, PageableQuery pageableQuery) {
        return userService.selectAll(userQuery, pageableQuery);
    }

    @GetMapping("/{id}")
    public User selectOne(@PathVariable Integer id) {
        return userService.selectOne(id);
    }


    @PostMapping
    public void insert(@Valid @RequestBody User user) {
        user.setId(null);
        userService.insert(user);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Valid @RequestBody User user) {
        user.setId(id);
        userService.update(user);
    }
}
