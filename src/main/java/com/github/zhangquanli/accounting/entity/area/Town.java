package com.github.zhangquanli.accounting.entity.area;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * 乡、镇
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
