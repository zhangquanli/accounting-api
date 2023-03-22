package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * 展示字段
 *
 * @author zhangquanli
 * @since 2023/2/27
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class DisplayColumn extends BaseEntity {
    /**
     * 字段名称，如：分公司名称
     */
    @NotNull
    @Column(nullable = false)
    private String name;
    /**
     * 字段代码，如：areaName
     */
    @NotNull
    @Column(nullable = false)
    private String code;
    /**
     * 组件编码
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String num;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private ComponentInfo componentInfo;
}
