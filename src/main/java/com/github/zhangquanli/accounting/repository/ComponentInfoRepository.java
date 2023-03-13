package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.base.ComponentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComponentInfoRepository extends JpaRepository<ComponentInfo, Integer>, JpaSpecificationExecutor<ComponentInfo> {
}
