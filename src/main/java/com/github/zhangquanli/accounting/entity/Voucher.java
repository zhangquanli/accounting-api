package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 凭证，一个凭证包含多个账目
 */
@Entity
@Getter
@Setter
public class Voucher extends BaseEntity {
    /**
     * 编号
     */
    @NotBlank
    @Column(nullable = false, unique = true)
    private String num;
    /**
     * 记账日期
     */
    @NotNull
    @Column(nullable = false)
    private LocalDate accountDate;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime createTime;

    /**
     * 会计分录集合
     */
    @NotEmpty
    @JsonIgnoreProperties({"voucher"})
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.PERSIST)
    private List<AccountingEntry> accountingEntries;

    /**
     * 所属账簿
     */
    @NotNull
    @ManyToOne
    private Account account;

    /**
     * 关联的原始凭证
     * <p>
     * 当前凭证为冲红凭证，关联凭证为原始凭证
     */
    @JsonIgnoreProperties({"originalVoucher", "invalidVoucher"})
    @ManyToOne
    private Voucher originalVoucher;

    /**
     * 关联的冲红凭证
     * <p>
     * 当前凭证为原始凭证，关联凭证为冲红凭证
     */
    @JsonIgnoreProperties({"originalVoucher", "invalidVoucher"})
    @ManyToOne
    private Voucher invalidVoucher;
}
