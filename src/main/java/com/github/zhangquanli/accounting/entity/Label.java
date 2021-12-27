package com.github.zhangquanli.accounting.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 标签
 *
 * @author zhangquanli
 * @since 2021/12/20 10:02:00
 */
@Entity
@Getter
@Setter
public class Label extends BaseEntity {

    /**
     * 名称
     */
    @Column(nullable = false, unique = true)
    private String name;

}
