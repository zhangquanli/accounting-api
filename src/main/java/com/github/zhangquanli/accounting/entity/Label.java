package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 标签
 */
@Table(name = "t_label")
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
     * 标签内容
     */
    @Column(nullable = false)
    private String content;

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
