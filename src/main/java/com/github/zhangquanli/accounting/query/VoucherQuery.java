package com.github.zhangquanli.accounting.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 凭证查询类
 */
@Getter
@Setter
public class VoucherQuery {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAccountDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAccountDate;

    private Integer accountId;
    private String num;
}
