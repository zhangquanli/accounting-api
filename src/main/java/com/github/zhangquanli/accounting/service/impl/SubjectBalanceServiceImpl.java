package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.Subject;
import com.github.zhangquanli.accounting.entity.SubjectBalance;
import com.github.zhangquanli.accounting.query.SubjectBalanceQuery;
import com.github.zhangquanli.accounting.repository.SubjectBalanceRepository;
import com.github.zhangquanli.accounting.repository.SubjectRepository;
import com.github.zhangquanli.accounting.service.SubjectBalanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 会计科目余额
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class SubjectBalanceServiceImpl implements SubjectBalanceService {
    private final SubjectRepository subjectRepository;
    private final SubjectBalanceRepository subjectBalanceRepository;

    public SubjectBalanceServiceImpl(SubjectRepository subjectRepository, SubjectBalanceRepository subjectBalanceRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectBalanceRepository = subjectBalanceRepository;
    }

    @Override
    public List<SubjectBalance> selectList(SubjectBalanceQuery subjectBalanceQuery) {
        List<SubjectBalance> subjectBalances = subjectBalanceRepository.findByAccount_Id(subjectBalanceQuery.getAccountId());
        if (subjectBalanceQuery.getIsRecursion() != null && subjectBalanceQuery.getIsRecursion()) {
            // 提取科目ID集合
            List<Integer> subjectIds = extractSubjectIds(subjectBalances);
            // 重新组装科目余额集合
            List<Subject> subjects = subjectRepository.findByIdIn(subjectIds);
            return subjects.stream()
                    .map(subject -> toSubjectBalance(subjectBalances, subject))
                    .collect(Collectors.toList());
        } else {
            return subjectBalances;
        }
    }

    private List<Integer> extractSubjectIds(List<SubjectBalance> subjectBalances) {
        return subjectBalances.stream()
                .map(subjectBalance -> subjectBalance.getSubject().getNum())
                .flatMap(num -> Stream.of(num.split("-")))
                .map(Integer::parseInt).distinct()
                .collect(Collectors.toList());
    }

    private SubjectBalance toSubjectBalance(List<SubjectBalance> subjectBalances, Subject subject) {
        return subjectBalances.stream()
                .filter(subjectBalance -> subject.getId().equals(subjectBalance.getSubject().getId()))
                .findAny()
                .orElseGet(() -> {
                    SubjectBalance subjectBalance = new SubjectBalance();
                    subjectBalance.setInitialAmount(BigDecimal.ZERO);
                    subjectBalance.setCurrentAmount(BigDecimal.ZERO);
                    subjectBalance.setSubject(subject);
                    return subjectBalance;
                });
    }
}
