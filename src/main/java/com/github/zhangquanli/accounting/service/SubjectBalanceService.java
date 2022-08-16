package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.SubjectBalance;
import com.github.zhangquanli.accounting.query.SubjectBalanceQuery;

import java.util.List;

/**
 * 会计科目余额
 *
 * @author zhangquanli
 * @since 2021/12/27 10:50:00
 */
public interface SubjectBalanceService {
    List<SubjectBalance> selectList(SubjectBalanceQuery subjectBalanceQuery);
}
