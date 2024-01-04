package com.github.zhangquanli.accounting.entity.area;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 区、县、自治县
 */
@Entity
@Getter
@Setter
public class District extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String code;


    @OneToMany(mappedBy = "district")
    private Set<Town> towns;
    @ManyToOne
    @JoinColumn(name = "city_code", referencedColumnName = "code")
    private City city;
}
