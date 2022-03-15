package com.github.zhangquanli.accounting.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 描述
 *
 * @author zhangquanli
 * @since 2022/1/5 10:07:00
 */
@Getter
@Setter
public class AccountingEntryQuery extends PageQuery {
    private Integer accountId;
    private String summary;
    private List<Integer> subjectIds;
    private List<Integer> labelIds;
}
