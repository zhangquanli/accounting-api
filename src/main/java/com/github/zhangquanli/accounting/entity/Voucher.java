package com.github.zhangquanli.accounting.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 凭证，一个凭证包含多个账目
 *
 * @author zhangquanli
 * @since 2021/12/20 13:35:00
 */
@Entity
@Getter
@Setter
public class Voucher extends BaseEntity {
    /**
     * 编号
     */
    @Column(nullable = false, unique = true)
    private String num;
    /**
     * 记账日期
     */
    @Column(nullable = false)
    private LocalDate accountDate;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    private LocalDateTime createTime;

    /**
     * 会计分录集合
     */
    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST)
    private List<AccountingEntry> accountingEntries;
}
