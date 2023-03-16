package com.github.zhangquanli.accounting.entity.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OptionType {
    private String value;
    private String label;
    private List<OptionType> children;
}
