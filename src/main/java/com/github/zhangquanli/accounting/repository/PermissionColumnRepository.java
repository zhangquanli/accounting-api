package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.base.PermissionColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionColumnRepository extends JpaRepository<PermissionColumn, Integer>, JpaSpecificationExecutor<PermissionColumn> {
}
