package com.github.zhangquanli.accounting.query;

import com.github.zhangquanli.accounting.entity.Category;
import lombok.Getter;
import lombok.Setter;

/**
 * 会计科目查询类
 *
 * @author zhangquanli
 * @since 2022/1/18 11:13:00
 */
@Getter
@Setter
public class SubjectQuery {
    private String code;
    private String name;
    private Category category;
}
