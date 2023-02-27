package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * 角色
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class Role extends BaseEntity {
    /**
     * 角色名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 关联的【数据字段】集合
     */
    @ManyToMany
    private Set<DataColumn> dataColumns;
}
