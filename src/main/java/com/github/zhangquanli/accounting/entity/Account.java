package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 会计账簿
 */
@Entity
@Getter
@Setter
public class Account extends BaseEntity {
    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;
    /**
     * 关联的会计余额集合
     */
    @JsonIgnoreProperties({"account"})
    @OneToMany(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SubjectBalance> subjectBalances;
}
