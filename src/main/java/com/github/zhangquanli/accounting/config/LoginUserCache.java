package com.github.zhangquanli.accounting.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 当前登录用户缓存
 *
 * @author zhangquanli
 * @since 2023/3/20
 */
public abstract class LoginUserCache {
    private static final Map<String, LoginUser> CACHE = new ConcurrentHashMap<>();

    public static void set(String username, LoginUser loginUser) {
        CACHE.put(username, loginUser);
    }

    public static LoginUser get(String username) {
        return CACHE.get(username);
    }
}
