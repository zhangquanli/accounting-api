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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    /**
     * 编号
     * <p>
     * 自关联字段，0表示根目录，使用主键字段，并用-隔开
     * 例：0-1-3
     */
    private String num;
    /**
     * 父级编码
     * <p>
     * 自关联字段，0表示根目录，使用主键字段，并用-隔开
     * 例：0-1-3
     */
    @NotNull
    @Column(nullable = false)
    private String parentNum;

    public enum Category {
        /**
         * 资产类
         */
        ASSETS,
        /**
         * 负债类
         */
        LIABILITY,
        /**
         * 所有者权益
         */
        OWNERS_EQUITY,
        /**
         * 成本类
         */
        COST,
        /**
         * 损益类
         */
        PROFIT_AND_LOSS,
    }

}
