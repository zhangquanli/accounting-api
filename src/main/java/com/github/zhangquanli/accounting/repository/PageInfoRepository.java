package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.base.PageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PageInfoRepository extends JpaRepository<PageInfo, Integer>, JpaSpecificationExecutor<PageInfo> {
}
