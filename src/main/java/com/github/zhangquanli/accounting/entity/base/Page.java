package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

/**
 * 页面
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class Page extends BaseEntity implements Serializable {
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
    @Column(nullable = false)
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
     * 关联的【接口】集合
     */
    @ManyToMany
    @JoinTable(name = "page_rel_api",
            joinColumns = @JoinColumn(name = "page_id"),
            inverseJoinColumns = @JoinColumn(name = "api_id"))
    private Set<Api> apis = Collections.emptySet();
    /**
     * 关联的【组件】集合
     */
    @Valid
    @OneToMany(mappedBy = "page", cascade = {PERSIST, MERGE}, orphanRemoval = true)
    private Set<Component> components = Collections.emptySet();
    /**
     * 关联的父级【页面】
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_code", referencedColumnName = "code")
    private Page parent;
    /**
     * 关联的子级【页面】集合
     */
    @OneToMany(mappedBy = "parent")
    private Set<Page> children = Collections.emptySet();

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
