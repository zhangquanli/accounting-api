package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    @JoinTable(name = "role_rel_page_info",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "page_info_id")})
    private List<PageInfo> pageInfos;
    /**
     * 关联的【组件】集合
     */
    @ManyToMany
    @JoinTable(name = "role_rel_component_info",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "component_info_id")})
    private List<ComponentInfo> componentInfos;
    /**
     * 关联的【展示字段】集合
     */
    @ManyToMany
    @JoinTable(name = "role_rel_display_column",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "display_column_id")})
    private List<DisplayColumn> displayColumns;
    /**
     * 关联的【权限字段】集合
     */
    @ManyToMany
    @JoinTable(name = "role_rel_permission_column",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_column_id")})
    private List<PermissionColumn> permissionColumns;


    /**
     * 父级角色
     */
    @ManyToOne
    private Role parent;
    /**
     * 子级角色集合
     */
    @OneToMany(mappedBy = "parent")
    private List<Role> children;
}
