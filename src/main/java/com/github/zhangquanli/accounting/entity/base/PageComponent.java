package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * 页面组件
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class PageComponent extends BaseEntity {
    private String name;
    private String type;

    /**
     * 关联的【页面信息】
     */
    @JsonIgnore
    @ManyToOne
    private PageInfo pageInfo;

    /**
     * 关联的【页面组件接口】集合
     */
    @OneToMany(mappedBy = "pageComponent")
    private Set<PageComponentApi> pageComponentApis;
}
