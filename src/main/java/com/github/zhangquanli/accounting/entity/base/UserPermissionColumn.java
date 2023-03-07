package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 用户的权限字段
 *
 * @author zhangquanli
 * @since 2023/3/2
 */
@Entity
@Getter
@Setter
public class UserPermissionColumn extends BaseEntity {
    /**
     * 权限字段的值
     */
    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PermissionColumn permissionColumn;
}
