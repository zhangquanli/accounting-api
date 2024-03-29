package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 科目仓库类
 */
public interface SubjectRepository extends JpaRepository<Subject, Integer>, JpaSpecificationExecutor<Subject> {
    List<Subject> findByIdIn(List<Integer> ids);
}
