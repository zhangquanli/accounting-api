package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * 用户关联角色
 *
 * @author zhangquanli
 * @since 2023/3/17
 */
@Entity
@Getter
@Setter
public class UserRelRole extends BaseEntity {
    /**
     * 权限字段的value
     */
    @NotNull
    @Column(nullable = false)
    private String value;
    /**
     * 权限字段的label
     */
    @NotNull
    @Column(nullable = false)
    private String label;
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;

    @JsonIgnore
    @ManyToOne
    private User user;
}
