package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 角色关联的页面
 *
 * @author zhangquanli
 * @since 2023/3/13
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class RoleRelPageInfo extends BaseEntity {
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
     * 页面
     */
    @NotNull
    @JsonIgnoreProperties({"permissionColumns", "apiInfos", "componentInfos", "parent", "children"})
    @ManyToOne
    @JoinColumn(nullable = false)
    private PageInfo pageInfo;
}
