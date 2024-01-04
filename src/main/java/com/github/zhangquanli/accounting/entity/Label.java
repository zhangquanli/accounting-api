package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 标签
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
