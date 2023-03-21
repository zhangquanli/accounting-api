package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.config.LoginUser;
import com.github.zhangquanli.accounting.config.LoginUserCache;
import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.entity.base.UserRelRole;
import com.github.zhangquanli.accounting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * 认证
 *
 * @author zhangquanli
 * @since 2023/3/17
 */
@RequestMapping("/authentication")
@RestController
public class AuthenticationController {
    private UserService userService;

    @Autowired
    public void setAuthenticationService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/currentRole")
    public UserRelRole getCurrentRole() {
        String username = getUsername();
        return LoginUserCache.get(username).getCurrentRole();
    }

    @PutMapping("/currentRole/{roleId}")
    public void setCurrentRole(@PathVariable Integer roleId) {
        String username = getUsername();
        User user = userService.selectByUsername(username);
        List<UserRelRole> userRelRoles = user.getUserRelRoles();
        UserRelRole currentRole = userRelRoles.stream()
                .filter(userRelRole -> userRelRole.getRole().getId().equals(roleId))
                .findAny().orElseThrow(EntityNotFoundException::new);
        LoginUser loginUser = LoginUser.builder().username(username)
                .currentUser(user).currentRole(currentRole).build();
        LoginUserCache.set(username, loginUser);
    }

    @GetMapping("/currentUser")
    public User getCurrentUser() {
        String username = getUsername();
        return LoginUserCache.get(username).getCurrentUser();
    }

    private String getUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }
}
