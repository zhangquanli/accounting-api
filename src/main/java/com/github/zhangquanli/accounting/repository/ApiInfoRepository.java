package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiInfoRepository extends JpaRepository<ApiInfo, Integer>, JpaSpecificationExecutor<ApiInfo> {
}
