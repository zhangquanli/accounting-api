package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 角色
 *
 * @author zhangquanli
 * @since 2023/2/28
 */
@Entity
@Getter
@Setter
public class Role extends BaseEntity {
    /**
     * 角色名称
     */
    @Column(nullable = false)
    private String name;


    /**
     * 关联的【页面】集合
     */
    @ManyToMany
    @JoinTable(name = "role_rel_page",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "page_id")})
    private Set<Page> pages;
    /**
     * 关联的【组件】集合
     */
    @ManyToMany
    @JoinTable(name = "role_rel_component",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "component_id")})
    private Set<Component> components;
    /**
     * 关联的【数据字段】集合
     */
    @ManyToMany
    @JoinTable(name = "role_rel_data_column",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "data_column_id")})
    private Set<DataColumn> columns;
    /**
     * 关联的【权限字段】集合
     */
    @ManyToMany
    @JoinTable(name = "role_rel_permission_column",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_column_id")})
    private Set<PermissionColumn> permissionColumns;


    /**
     * 父级角色
     */
    @ManyToOne
    private Role parent;
    /**
     * 子级角色集合
     */
    @OneToMany(mappedBy = "parent")
    private Set<Role> children;
}
