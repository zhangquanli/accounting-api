package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * 组件信息
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class ComponentInfo extends BaseEntity {
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
    @Column(nullable = false, unique = true)
    private String code;
    /**
     * 组件编码
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String num;


    /**
     * 关联的【接口】集合
     */
    @ManyToMany
    @JoinTable(name = "component_info_rel_api_info",
            joinColumns = @JoinColumn(name = "component_info_id"),
            inverseJoinColumns = @JoinColumn(name = "api_info_id"))
    private List<ApiInfo> apiInfos = Collections.emptyList();
    /**
     * 关联的【展示字段】集合
     */
    @Valid
    @OneToMany(mappedBy = "componentInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<DisplayColumn> displayColumns = Collections.emptyList();


    /**
     * 关联的【页面】
     */
    @JsonIgnore
    @ManyToOne
    private PageInfo pageInfo;
}
