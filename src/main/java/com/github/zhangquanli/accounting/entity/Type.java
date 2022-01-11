package com.github.zhangquanli.accounting.entity;

import java.math.BigDecimal;

/**
 * 借贷类型
 *
 * @author zhangquanli
 * @since 2021/12/22 8:51:00
 */
public enum Type {
    /**
     * 借
     */
    DEBIT(1),
    /**
     * 贷
     */
    CREDIT(-1),
    ;

    private final Integer value;

    Type(Integer value) {
        this.value = value;
    }

    public BigDecimal symbol() {
        return BigDecimal.valueOf(value);
    }

    public BigDecimal negativeSymbol() {
        return BigDecimal.valueOf(value).negate();
    }
}
