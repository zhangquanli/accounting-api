package com.github.zhangquanli.accounting.entity.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionType {
    private String value;
    private String label;
    private Level level;

    @Builder
    @Getter
    @Setter
    public static class Level {
        private String value;
        private String label;
    }
}
