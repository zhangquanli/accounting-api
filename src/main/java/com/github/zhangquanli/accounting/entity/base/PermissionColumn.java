package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * 权限字段
 *
 * @author zhangquanli
 * @since 2023/3/2
 */
@Entity
@Getter
@Setter
public class PermissionColumn extends BaseEntity {
    /**
     * 字段名称，如：省
     */
    @Column(nullable = false)
    private String name;
    /**
     * 字段代码，如：province
     */
    @Column(nullable = false)
    private String code;
    /**
     * 字段分类，如：地域
     */
    @Column(nullable = false)
    private String category;
    /**
     * 查询SQL，如：select name as label, code as value from province
     */
    @Column(nullable = false)
    private String querySql;


    @JsonIgnoreProperties({"parent", "children"})
    @OneToMany(mappedBy = "parent")
    private Set<PermissionColumn> children;
    @JsonIgnoreProperties({"parent", "children"})
    @ManyToOne
    private PermissionColumn parent;
}
