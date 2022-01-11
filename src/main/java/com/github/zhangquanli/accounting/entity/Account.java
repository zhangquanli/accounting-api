package com.github.zhangquanli.accounting.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 会计账簿
 *
 * @author zhangquanli
 * @since 2021/12/22 9:42:00
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
    @OneToMany(mappedBy = "account", cascade = {CascadeType.PERSIST})
    private List<SubjectBalance> subjectBalances;

}
