package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.SubjectBalance;
import com.github.zhangquanli.accounting.query.SubjectBalanceQuery;
import com.github.zhangquanli.accounting.service.SubjectBalanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会计科目余额
 */
@RequestMapping("/subjectBalances")
@RestController
public class SubjectBalanceController {
    private final SubjectBalanceService subjectBalanceService;

    public SubjectBalanceController(SubjectBalanceService subjectBalanceService) {
        this.subjectBalanceService = subjectBalanceService;
    }

    @GetMapping
    public List<SubjectBalance> selectList(SubjectBalanceQuery subjectBalanceQuery) {
        return subjectBalanceService.selectList(subjectBalanceQuery);
    }
}
