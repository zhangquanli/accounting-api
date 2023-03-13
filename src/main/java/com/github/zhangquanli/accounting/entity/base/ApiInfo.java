package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * 接口信息
 *
 * @author zhangquanli
 * @since 2023/2/27
 */
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HttpMethod httpMethod;
}
