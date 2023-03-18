package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证
 *
 * @author zhangquanli
 * @since 2023/3/17
 */
@RequestMapping("/authentication")
@RestController
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/getUserInfo")
    public User getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        return authenticationService.getUserInfo(username);
    }
}
