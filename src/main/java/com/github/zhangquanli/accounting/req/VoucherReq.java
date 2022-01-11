package com.github.zhangquanli.accounting.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 凭证请求封装类
 *
 * @author zhangquanli
 * @since 2021/12/31 17:54:00
 */
@Getter
@Setter
public class VoucherReq extends PageReq {
    private String num;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAccountDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAccountDate;
}
