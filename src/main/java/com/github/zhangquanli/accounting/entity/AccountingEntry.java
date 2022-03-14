package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
     * 摘要
     */
    private String summary;
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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime createTime;
    /**
     * 科目余额
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private SubjectBalance subjectBalance;
    /**
     * 所属凭证
     */
    @JsonIgnoreProperties({"accountingEntries", "originalVoucher", "invalidVoucher"})
    @ManyToOne
    @JoinColumn(nullable = false)
    private Voucher voucher;
    /**
     * 标签集合
     */
    @JsonIgnoreProperties({"accountingEntries"})
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Label> labels;

    /**
     * 关联的原始会计分录
     * <p>
     * 当前会计分录为冲红的，关联会计分录为原始的
     */
    @JsonIgnoreProperties({"originalAccountingEntry", "invalidAccountingEntry"})
    @ManyToOne
    private AccountingEntry originalAccountingEntry;

    /**
     * 关联的冲红会计分录
     * <p>
     * 当前会计分录为原始的，关联会计分录为冲红的
     */
    @JsonIgnoreProperties({"originalAccountingEntry", "invalidAccountingEntry"})
    @ManyToOne
    private AccountingEntry invalidAccountingEntry;

}
