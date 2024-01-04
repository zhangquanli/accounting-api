package com.github.zhangquanli.accounting.entity.area;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 市、地区
 */
@Entity
@Getter
@Setter
public class City extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String code;


    @OneToMany(mappedBy = "city")
    private Set<District> districts;
    @ManyToOne
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    private Province province;
}
