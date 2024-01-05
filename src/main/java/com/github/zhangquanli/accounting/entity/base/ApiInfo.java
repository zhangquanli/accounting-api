package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口信息
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class ApiInfo extends BaseEntity {
    /**
     * 接口名称
     */
    @NotNull
    @Column(nullable = false)
    private String name;
    /**
     * 接口地址
     */
    @NotNull
    @Column(nullable = false)
    private String url;
    /**
     * HTTP方法
     */
    @NotNull
    @Column(nullable = false)
    private String httpMethod;
}
