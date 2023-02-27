package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 组件
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class Component extends BaseEntity {
    /**
     * 组件名称
     */
    @Column(nullable = false)
    private String name;
    /**
     * 组件代码
     */
    @Column(nullable = false)
    private String code;

    /**
     * 关联的【接口】集合
     */
    @ManyToMany
    @JoinTable(name = "component_rel_api",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "api_id"))
    private Set<Api> apis;
    /**
     * 关联的【页面】
     */
    @JsonIgnore
    @ManyToOne
    private Page page;
}
