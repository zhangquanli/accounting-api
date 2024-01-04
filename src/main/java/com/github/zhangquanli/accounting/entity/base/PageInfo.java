package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 页面信息
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class PageInfo extends BaseEntity implements Serializable {
    /**
     * 页面名称
     */
    @NotNull
    @Column(nullable = false)
    private String name;
    /**
     * 页面代码
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String code;
    /**
     * 页面类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
    /**
     * 页面地址
     */
    @NotNull
    @Column(nullable = false)
    private String url;


    /**
     * 关联的【权限字段】集合
     */
    @JsonIgnoreProperties({"children", "parent"})
    @ManyToMany
    @JoinTable(name = "page_info_rel_permission_column",
            joinColumns = @JoinColumn(name = "page_info_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_column_id"))
    private List<PermissionColumn> permissionColumns = Collections.emptyList();
    /**
     * 关联的【接口】集合
     */
    @ManyToMany
    @JoinTable(name = "page_info_rel_api_info",
            joinColumns = @JoinColumn(name = "page_info_id"),
            inverseJoinColumns = @JoinColumn(name = "api_info_id"))
    private List<ApiInfo> apiInfos = Collections.emptyList();
    /**
     * 关联的【组件】集合
     */
    @Valid
    @OneToMany(mappedBy = "pageInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ComponentInfo> componentInfos = Collections.emptyList();


    /**
     * 关联的父级【页面】
     */
    @JsonIgnoreProperties({"parent", "children"})
    @ManyToOne
    private PageInfo parent;
    /**
     * 关联的子级【页面】集合
     */
    @OneToMany(mappedBy = "parent")
    private List<PageInfo> children = Collections.emptyList();


    public enum Type {
        /**
         * 虚拟
         */
        VIRTUALITY,
        /**
         * 真实
         */
        REALITY
    }
}
