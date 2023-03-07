package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

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
    @NotNull
    @Column(nullable = false)
    private String name;
    /**
     * 组件代码
     */
    @NotNull
    @Column(nullable = false)
    private String code;

    /**
     * 关联的【接口】集合
     */
    @Valid
    @ManyToMany
    @JoinTable(name = "component_rel_api",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "api_id"))
    private Set<Api> apis = Collections.emptySet();
    /**
     * 关联的【数据字段】集合
     */
    @Valid
    @OneToMany(mappedBy = "component", cascade = {PERSIST, MERGE})
    private Set<DataColumn> dataColumns = Collections.emptySet();
    /**
     * 关联的【页面】
     */
    @JsonIgnore
    @ManyToOne
    private Page page;
}
