package com.github.zhangquanli.accounting.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

/**
 * 分页请求封装类
 */
@Getter
@Setter
public class PageableQuery {
    private Integer page;
    private Integer size;

    public static boolean pageable(PageableQuery pageableQuery) {
        Integer page = pageableQuery.getPage();
        Integer size = pageableQuery.getSize();
        return page != null && size != null;
    }

    public static Pageable toPageable(PageableQuery pageableQuery) {
        Integer page = pageableQuery.getPage();
        Assert.notNull(page, "page can not be null");
        Integer size = pageableQuery.getSize();
        Assert.notNull(size, "size can not be null");
        return PageRequest.of(page - 1, size);
    }
}
