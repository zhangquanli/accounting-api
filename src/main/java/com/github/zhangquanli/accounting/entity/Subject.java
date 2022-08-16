package com.github.zhangquanli.accounting.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * 会计科目
 * <p>
 * 会计科目最多只能有四级
 *
 * @author zhangquanli
 * @since 2021/12/20 10:01:00
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
