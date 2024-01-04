package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Subject;
import com.github.zhangquanli.accounting.query.SubjectQuery;
import com.github.zhangquanli.accounting.service.SubjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会计科目管理
 */
@RequestMapping("/subjects")
@RestController
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> selectList(SubjectQuery subjectQuery) {
        return subjectService.selectList(subjectQuery);
    }

    @PostMapping
    public void insert(@Validated @RequestBody Subject subject) {
        subject.setId(null);
        subjectService.insert(subject);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Validated @RequestBody Subject subject) {
        subject.setId(id);
        subjectService.update(subject);
    }

    @GetMapping("/{id}")
    public Subject selectOne(@PathVariable Integer id) {
        return subjectService.selectOne(id);
    }
}
