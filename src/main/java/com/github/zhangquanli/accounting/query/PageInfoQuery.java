package com.github.zhangquanli.accounting.query;

import com.github.zhangquanli.accounting.entity.base.PageInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfoQuery {
    private String name;
    private String code;
    private PageInfo.Type type;
    private String url;
}
