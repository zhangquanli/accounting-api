package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 科目余额
 */
@Entity
@Getter
@Setter
public class SubjectBalance extends BaseEntity {
    /**
     * 初始金额
     */
    @Column(nullable = false)
    private BigDecimal initialAmount;
    /**
     * 当前金额
     */
    @Column(nullable = false)
    private BigDecimal currentAmount;
    /**
     * 会计科目
     */
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Subject subject;
    /**
     * 账簿
     */
    @JsonIgnoreProperties({"subjectBalances"})
    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;
}
