package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色关联的组件
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class RoleRelComponentInfo extends BaseEntity {
    /**
     * 勾选类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CheckedType checkedType;


    /**
     * 角色
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;
    /**
     * 组件
     */
    @NotNull
    @JsonIgnoreProperties({"apiInfos", "displayColumns", "pageInfo"})
    @ManyToOne
    @JoinColumn(nullable = false)
    private ComponentInfo componentInfo;
}
