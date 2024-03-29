package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 权限字段
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class PermissionColumn extends BaseEntity {
    /**
     * 权限名称
     */
    @Column(nullable = false)
    private String name;
    /**
     * 权限等级，如：分公司
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionLevel level;


    @JsonIgnoreProperties({"parent", "children"})
    @ManyToOne
    private PermissionColumn parent;
    @JsonIgnoreProperties({"parent"})
    @OneToMany(mappedBy = "parent")
    private List<PermissionColumn> children;
}
