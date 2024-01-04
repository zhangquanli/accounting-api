package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * 角色
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
     * 权限字段
     */
    @JsonIgnoreProperties({"parent", "children"})
    @NotNull
    @ManyToOne
    private PermissionColumn permissionColumn;


    /**
     * 关联的【页面】集合
     */
    @JsonIgnoreProperties({"role"})
    @Valid
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RoleRelPageInfo> roleRelPageInfos = Collections.emptyList();
    /**
     * 关联的【组件】集合
     */
    @JsonIgnoreProperties({"role"})
    @Valid
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RoleRelComponentInfo> roleRelComponentInfos = Collections.emptyList();
    /**
     * 关联的【展示字段】集合
     */
    @JsonIgnoreProperties({"role"})
    @Valid
    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RoleRelDisplayColumn> roleRelDisplayColumns = Collections.emptyList();


    /**
     * 父级角色
     */
    @JsonIgnoreProperties({"roleRelPageInfos", "roleRelComponentInfos", "roleRelDisplayColumns", "parent", "children"})
    @ManyToOne
    private Role parent;
    /**
     * 子级角色集合
     */
    @JsonIgnoreProperties({"roleRelPageInfos", "roleRelComponentInfos", "roleRelDisplayColumns", "parent"})
    @OneToMany(mappedBy = "parent")
    private List<Role> children;
}
