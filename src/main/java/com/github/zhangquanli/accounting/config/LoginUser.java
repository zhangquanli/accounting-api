package com.github.zhangquanli.accounting.config;

import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.entity.base.UserRelRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 当前登录用户
 *
 * @author zhangquanli
 * @since 2023/3/21
 */
@Builder
@Getter
@Setter
public class LoginUser {
    private String username;
    private User currentUser;
    private UserRelRole currentRole;
}
