package com.github.zhangquanli.accounting.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Collection;

/**
 * 用户
 *
 * @author zhangquanli
 * @since 2022/3/22 16:45:00
 */
@Entity
public class User extends BaseEntity implements UserDetails {

    /**
     * 账号
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 密码
     */
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(
                new SimpleGrantedAuthority("role1"),
                new SimpleGrantedAuthority("role2"));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
