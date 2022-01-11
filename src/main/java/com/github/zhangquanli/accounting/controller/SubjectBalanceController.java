package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.SubjectBalance;
import com.github.zhangquanli.accounting.service.SubjectBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会计科目余额
 *
 * @author zhangquanli
 * @since 2021/12/27 10:47:00
 */
@CrossOrigin
@RequestMapping("/subjectBalances")
@RestController
public class SubjectBalanceController {

    private SubjectBalanceService subjectBalanceService;

    @Autowired
    public void setSubjectBalanceService(SubjectBalanceService subjectBalanceService) {
        this.subjectBalanceService = subjectBalanceService;
    }

    @GetMapping
    public List<SubjectBalance> select(@RequestParam Integer accountId) {
        return subjectBalanceService.select(accountId);
    }

}
