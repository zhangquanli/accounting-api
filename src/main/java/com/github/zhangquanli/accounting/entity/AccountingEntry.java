package com.github.zhangquanli.accounting.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会计分录
 * 一个会计分录可以关联一个科目和一个凭证，一个会计分录可以关联多个标签
 *
 * @author zhangquanli
 * @since 2021/12/20 13:40:00
 */
@Entity
@Getter
@Setter
public class AccountingEntry extends BaseEntity {

    /**
     * 类型
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
    /**
     * 金额
     */
    @Column(nullable = false)
    private BigDecimal amount;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 科目
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Subject subject;
    /**
     * 标签集合
     */
    @ManyToMany
    private List<Label> labels;
    /**
     * 凭证
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private Voucher voucher;

}
