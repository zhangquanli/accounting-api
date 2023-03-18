package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.base.User;

public interface AuthenticationService {
    User getUserInfo(String username);
}
