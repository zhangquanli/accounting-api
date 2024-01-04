package com.github.zhangquanli.accounting.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述
 */
@Getter
@Setter
public class SubjectBalanceQuery {
    /**
     * 账簿主键
     */
    private Integer accountId;
    /**
     * 是否递归查询上级数据
     */
    private Boolean isRecursion;
}
