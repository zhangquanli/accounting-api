package com.github.zhangquanli.accounting.entity.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionType {
    private String value;
    private String label;
    private PermissionLevel level;
}
