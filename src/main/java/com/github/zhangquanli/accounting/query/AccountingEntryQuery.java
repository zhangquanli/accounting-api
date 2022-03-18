package com.github.zhangquanli.accounting.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 描述
 *
 * @author zhangquanli
 * @since 2022/1/5 10:07:00
 */
@Getter
@Setter
public class AccountingEntryQuery  {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endVoucherDate;

    private Integer accountId;
    private Integer subjectId;
    private Integer labelId;
    private String summary;
}
