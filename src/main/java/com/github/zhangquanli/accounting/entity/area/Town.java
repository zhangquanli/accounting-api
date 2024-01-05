package com.github.zhangquanli.accounting.entity.area;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 乡、镇
 */
@Table(name = "a_town")
@Entity
@Getter
@Setter
public class Town extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String content;


    @ManyToOne
    @JoinColumn(name = "district_content", referencedColumnName = "content")
    private District district;
}
