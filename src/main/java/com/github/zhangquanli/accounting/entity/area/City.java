package com.github.zhangquanli.accounting.entity.area;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 市、地区
 */
@Table(name = "a_city")
@Entity
@Getter
@Setter
public class City extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String content;


    @OneToMany(mappedBy = "city")
    private Set<District> districts;
    @ManyToOne
    @JoinColumn(name = "province_content", referencedColumnName = "content")
    private Province province;
}
