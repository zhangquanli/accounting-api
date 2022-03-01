package com.github.zhangquanli.accounting.query;

import com.github.zhangquanli.accounting.entity.Type;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 描述
 *
 * @author zhangquanli
 * @since 2022/1/5 10:07:00
 */
@Getter
@Setter
public class AccountingEntryQuery {

    /**
     * 借贷类型
     */
    private Type type;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 科目编号
     */
    private String subjectNum;
    /**
     * 凭证编号
     */
    private String voucherNum;
    /**
     * 标签集合
     */
    private List<String> labelNames;

}
