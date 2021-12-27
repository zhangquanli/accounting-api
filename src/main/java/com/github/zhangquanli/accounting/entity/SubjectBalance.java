package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 科目余额
 *
 * @author zhangquanli
 * @since 2021/12/22 8:50:00
 */
@Entity
@Getter
@Setter
public class SubjectBalance extends BaseEntity {

    /**
     * 期初类型
     */
    @Column(nullable = false)
    private Type initialType;
    /**
     * 期初金额
     */
    @Column(nullable = false)
    private BigDecimal initialAmount;
    /**
     * 当前类型
     */
    @Column(nullable = false)
    private Type currentType;
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
     * 关联的会计账簿
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

}
