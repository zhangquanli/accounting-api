package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 页面组件接口
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class PageComponentApi extends BaseEntity {
    private String name;
    private String url;

    /**
     * 关联的【页面组件】
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private PageComponent pageComponent;
}
