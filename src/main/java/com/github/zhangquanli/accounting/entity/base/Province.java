package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * 省、直辖市、自治区
 *
 * @author zhangquanli
 * @since 2023/3/2
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
