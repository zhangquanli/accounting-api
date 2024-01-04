package com.github.zhangquanli.accounting.entity;

import java.math.BigDecimal;

/**
 * 会计分类
 * <p>
 * 资产类、成本类科目，借方增加，贷方减少
 * 债类、所有者权益类、损益类科目，借方减少，贷方增加
 */
public enum Category {
    /**
     * 资产类
     */
    ASSETS {
        @Override
        public BigDecimal symbol(Type type) {
            return type.symbol();
        }
    },
    /**
     * 负债类
     */
    LIABILITY {
        @Override
        public BigDecimal symbol(Type type) {
            return type.negativeSymbol();
        }
    },
    /**
     * 共同类
     */
    COMMON {
        @Override
        public BigDecimal symbol(Type type) {
            return type.symbol();
        }
    },
    /**
     * 所有者权益类
     */
    OWNERS_EQUITY {
        @Override
        public BigDecimal symbol(Type type) {
            return type.negativeSymbol();
        }
    },
    /**
     * 成本类
     */
    COST {
        @Override
        public BigDecimal symbol(Type type) {
            return type.symbol();
        }
    },
    /**
     * 损益类
     */
    PROFIT_AND_LOSS {
        @Override
        public BigDecimal symbol(Type type) {
            return type.negativeSymbol();
        }
    },
    ;

    public abstract BigDecimal symbol(Type type);
}
