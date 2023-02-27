package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 页面
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class Page extends BaseEntity {
    /**
     * 页面名称
     */
    @Column(nullable = false)
    private String name;
    /**
     * 页面地址
     */
    private String url;


    /**
     * 关联的【接口】集合
     */
    @ManyToMany
    @JoinTable(name = "page_rel_api",
            joinColumns = @JoinColumn(name = "page_id"),
            inverseJoinColumns = @JoinColumn(name = "api_id"))
    private Set<Api> apis;
    /**
     * 关联的【组件】集合
     */
    @OneToMany(mappedBy = "page")
    private Set<Component> components;
}
