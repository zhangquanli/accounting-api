package com.github.zhangquanli.accounting.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.zhangquanli.accounting.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户关联角色
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "t_user_rel_role")
@Entity
@Getter
@Setter
public class UserRelRole extends BaseEntity {
    /**
     * 权限字段，如：510104
     */
    @NotNull
    @Column(nullable = false)
    private String permissionCode;
    /**
     * 权限字段，如：锦江区
     */
    @NotNull
    @Column(nullable = false)
    private String permissionName;
    /**
     * 完整的权限字段，如：0,51,5101,510104
     */
    private String fullPermissionCode;
    /**
     * 完整的权限字段，如：全部,四川省,成都市,锦江区
     */
    private String fullPermissionName;
    @NotNull
    @JsonIgnoreProperties({"roleRelPageInfos", "roleRelComponentInfos", "roleRelDisplayColumns", "parent", "children"})
    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;


    @JsonIgnoreProperties({"userRelRoles"})
    @ManyToOne
    private User user;
}
