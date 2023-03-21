package com.github.zhangquanli.accounting.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
@Setter
public class ListResult<T> {
    private Long total;
    private List<T> rows;

    public static <T> ListResult<T> create(List<T> list) {
        long total = list.size();
        return ListResult.<T>builder().total(total).rows(list).build();
    }

    public static <T> ListResult<T> create(Page<T> page) {
        long total = page.getTotalElements();
        return ListResult.<T>builder().total(total).rows(page.getContent()).build();
    }
}
