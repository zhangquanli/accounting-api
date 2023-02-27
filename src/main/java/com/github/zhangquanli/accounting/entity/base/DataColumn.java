package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * 数据字段
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class DataColumn extends BaseEntity {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;
}
