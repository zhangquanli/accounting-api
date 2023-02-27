package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * 页面信息
 *
 * @author zhangquanli
 * @since 2023/2/24
 */
@Entity
@Getter
@Setter
public class PageInfo extends BaseEntity {
    /**
     * 页面名称
     */
    @Column(nullable = false)
    private String name;
    /**
     * 页面地址
     */
    private String url;
    /**
     * 页面类型：虚拟页面和真实页面
     */
    @Column(nullable = false)
    private String type;

    /**
     * 编码
     * <p>
     * 自关联字段，'0'表示根目录，使用主键字段，并用'-'隔开
     * 例：0-1-3
     */
    private String num;
    /**
     * 父级编码
     * <p>
     * 自关联字段，'0'表示根目录，使用主键字段，并用'-'隔开
     * 例：0-1
     */
    @Column(nullable = false)
    private String parentNum;

    /**
     * 关联的【页面组件】集合
     */
    @OneToMany(mappedBy = "pageInfo")
    private Set<PageComponent> pageComponents;

    /**
     * 关联的【页面接口】集合
     */
    @OneToMany(mappedBy = "pageInfo")
    private Set<PageApi> pageApis;
}
