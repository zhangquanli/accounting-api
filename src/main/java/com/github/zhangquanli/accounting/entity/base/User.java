package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 用户
 *
 * @author zhangquanli
 * @since 2022/3/22 16:45:00
 */
@Entity
@Getter
@Setter
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
    /**
     * 关联的【角色】集合
     */
    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<UserRelRole> roles;

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
