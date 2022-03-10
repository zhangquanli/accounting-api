package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

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
     * 标签名
     */
    @Column(nullable = false)
    private String name;

    /**
     * 标签值
     */
    @Column(nullable = false)
    private String value;

    /**
     * 标签标识
     * <p>
     * 由name和value组成，例：报销人-张三
     */
    @Column(nullable = false, unique = true)
    private String mark;

    /**
     * 会计分录集合
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "labels")
    private List<AccountingEntry> accountingEntries;

}
