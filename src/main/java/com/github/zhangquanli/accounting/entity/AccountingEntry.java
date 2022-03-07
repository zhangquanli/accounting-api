package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会计分录
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
     * 余额
     */
    @Column(nullable = false)
    private BigDecimal balance;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 科目余额
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private SubjectBalance subjectBalance;
    /**
     * 所属凭证
     */
    @JsonIgnoreProperties("accountingEntries")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Voucher voucher;
    /**
     * 标签集合
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Label> labels;

}
