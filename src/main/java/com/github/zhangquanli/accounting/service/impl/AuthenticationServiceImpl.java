package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.repository.UserRepository;
import com.github.zhangquanli.accounting.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserInfo(String username) {
        return userRepository.findByUsername(username);
    }
}
