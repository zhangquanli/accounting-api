package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.SubjectBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * 会计余额仓库类
 */
public interface SubjectBalanceRepository extends JpaRepository<SubjectBalance, Integer>,
        JpaSpecificationExecutor<SubjectBalance> {
    List<SubjectBalance> findByAccount_Id(Integer accountId);

    Optional<SubjectBalance> findBySubject_IdAndAccount_Id(Integer subjectId, Integer accountId);
}
