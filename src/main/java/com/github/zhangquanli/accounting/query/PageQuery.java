package com.github.zhangquanli.accounting.query;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 分页请求封装类
 *
 * @author zhangquanli
 * @since 2021/12/31 18:11:00
 */
@Getter
@Setter
public class PageQuery {
    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
}
