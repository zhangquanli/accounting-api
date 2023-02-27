package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 接口
 *
 * @author zhangquanli
 * @since 2023/2/27
 */
@Entity
@Getter
@Setter
public class Api extends BaseEntity {
    /**
     * 接口名称
     */
    @Column(nullable = false)
    private String name;
    /**
     * 接口地址
     */
    @Column(nullable = false)
    private String url;
}
