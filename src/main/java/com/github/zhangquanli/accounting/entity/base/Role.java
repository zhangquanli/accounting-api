package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
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
    @NotNull
    @Column(nullable = false)
    private String name;
    /**
     * 角色代码
     */
    @NotNull
    @Column(nullable = false)
    private String code;


    /**
     * 关联的【页面】集合
     */
    @Valid
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RoleRelPageInfo> pageInfos = Collections.emptyList();
    /**
     * 关联的【组件】集合
     */
    @Valid
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RoleRelComponentInfo> componentInfos = Collections.emptyList();
    /**
     * 关联的【展示字段】集合
     */
    @Valid
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RoleRelDisplayColumn> displayColumns = Collections.emptyList();


//    /**
//     * 关联的【权限字段】集合
//     */
//    @ManyToMany
//    @JoinTable(name = "role_rel_permission_column",
//            joinColumns = {@JoinColumn(name = "role_id")},
//            inverseJoinColumns = {@JoinColumn(name = "permission_column_id")})
//    private List<PermissionColumn> permissionColumns;


    /**
     * 父级角色
     */
    @JsonIgnoreProperties({"parent", "children"})
    @ManyToOne
    private Role parent;
    /**
     * 子级角色集合
     */
    @OneToMany(mappedBy = "parent")
    private List<Role> children;
}
