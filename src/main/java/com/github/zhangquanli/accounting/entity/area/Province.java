package com.github.zhangquanli.accounting.entity.area;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 省、直辖市、自治区
 */
@Entity
@Getter
@Setter
public class Province extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String code;


    @OneToMany(mappedBy = "province")
    private Set<City> cities;
}
