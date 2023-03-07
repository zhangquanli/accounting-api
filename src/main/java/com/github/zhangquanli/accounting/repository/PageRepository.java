package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.base.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PageRepository extends JpaRepository<Page, Integer>, JpaSpecificationExecutor<Page> {
}
