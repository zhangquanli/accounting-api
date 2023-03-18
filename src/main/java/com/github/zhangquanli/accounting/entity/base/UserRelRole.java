package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * 用户关联角色
 *
 * @author zhangquanli
 * @since 2023/3/17
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class UserRelRole extends BaseEntity {
    /**
     * 权限字段，如：510101
     */
    @NotNull
    @Column(nullable = false)
    private String value;
    /**
     * 权限字段，如：锦江区
     */
    @NotNull
    @Column(nullable = false)
    private String label;
    /**
     * 完整的权限字段，如：0,51,5101,510101
     */
    private String fullValue;
    /**
     * 完整的权限字段，如：全部,四川省,成都市,锦江区
     */
    private String fullLabel;
    @JsonIgnoreProperties({"roleRelPageInfos", "roleRelComponentInfos", "roleRelDisplayColumns", "parent", "children"})
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;


    @JsonIgnore
    @ManyToOne
    private User user;
}
