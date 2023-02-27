package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 用户数据字段
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class UserDataColumn extends BaseEntity {
    /**
     * 字段值
     */
    private String value;

    /**
     * 关联的数据字段
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private DataColumn dataColumn;

    /**
     * 关联的用户
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
