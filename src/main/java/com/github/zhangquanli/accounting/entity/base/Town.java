package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 乡、镇
 *
 * @author zhangquanli
 * @since 2023/3/2
 */
@Entity
@Getter
@Setter
public class Town extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String code;


    @ManyToOne
    @JoinColumn(name = "district_code", referencedColumnName = "code")
    private District district;
}
