package com.github.zhangquanli.accounting.entity.area;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 区、县、自治县
 */
@Table(name = "a_district")
@Entity
@Getter
@Setter
public class District extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String content;


    @OneToMany(mappedBy = "district")
    private Set<Town> towns;
    @ManyToOne
    @JoinColumn(name = "city_content", referencedColumnName = "content")
    private City city;
}
