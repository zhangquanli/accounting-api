package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.SubjectBalance;
import com.github.zhangquanli.accounting.repository.SubjectBalanceRepository;
import com.github.zhangquanli.accounting.service.SubjectBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会计科目余额
 *
 * @author zhangquanli
 * @since 2021/12/27 10:51:00
 */
@Service
public class SubjectBalanceServiceImpl implements SubjectBalanceService {

    private SubjectBalanceRepository subjectBalanceRepository;

    @Autowired
    public void setSubjectBalanceRepository(SubjectBalanceRepository subjectBalanceRepository) {
        this.subjectBalanceRepository = subjectBalanceRepository;
    }

    @Override
    public List<SubjectBalance> select(Integer accountId) {
        return subjectBalanceRepository.findByAccount_Id(accountId);
    }
    
}
