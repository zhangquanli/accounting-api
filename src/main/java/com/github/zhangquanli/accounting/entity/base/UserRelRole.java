package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户关联角色
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class UserRelRole extends BaseEntity {
    /**
     * 权限字段，如：510104
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
     * 完整的权限字段，如：0,51,5101,510104
     */
    private String fullValue;
    /**
     * 完整的权限字段，如：全部,四川省,成都市,锦江区
     */
    private String fullLabel;
    @NotNull
    @JsonIgnoreProperties({"roleRelPageInfos", "roleRelComponentInfos", "roleRelDisplayColumns", "parent", "children"})
    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;


    @JsonIgnoreProperties({"userRelRoles"})
    @ManyToOne
    private User user;
}
