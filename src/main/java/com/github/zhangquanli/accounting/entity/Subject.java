package com.github.zhangquanli.accounting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 会计科目
 * <p>
 * 会计科目最多只能有四级
 */
@Entity
@Getter
@Setter
public class Subject extends BaseEntity {
    /**
     * 代码
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String code;
    /**
     * 名称
     */
    @NotNull
    @Column(nullable = false)
    private String name;
    /**
     * 分类
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    /**
     * 适用范围
     */
    private String scope;
    /**
     * 编号
     * <p>
     * 自关联字段，'0'表示根目录，使用主键字段，并用'-'隔开
     * 例：0-1-3
     */
    private String num;
    /**
     * 父级编码
     * <p>
     * 自关联字段，'0'表示根目录，使用主键字段，并用'-'隔开
     * 例：0-1
     */
    @NotNull
    @Column(nullable = false)
    private String parentNum;
}
