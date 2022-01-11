package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @JsonIgnoreProperties("voucher")
    @OneToMany(mappedBy = "voucher", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<AccountingEntry> accountingEntries;
}
