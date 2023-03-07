package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.base.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApiRepository extends JpaRepository<Api, Integer>, JpaSpecificationExecutor<Api> {
}
