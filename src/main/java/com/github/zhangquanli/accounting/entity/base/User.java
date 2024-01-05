package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 用户
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "b_user")
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
    @Valid
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserRelRole> userRelRoles;

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
