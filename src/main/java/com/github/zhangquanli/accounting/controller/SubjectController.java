package com.github.zhangquanli.accounting.controller;

import com.github.zhangquanli.accounting.entity.Subject;
import com.github.zhangquanli.accounting.query.SubjectQuery;
import com.github.zhangquanli.accounting.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会计科目管理
 *
 * @author zhangquanli
 * @since 2021/12/20 11:14:00
 */
@RequestMapping("/subjects")
@RestController
public class SubjectController {

    private SubjectService subjectService;

    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> select(SubjectQuery subjectQuery) {
        return subjectService.select(subjectQuery);
    }

    @PostMapping
    public void insert(@Validated @RequestBody Subject subject) {
        subjectService.insert(subject);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @Validated @RequestBody Subject subject) {
        subjectService.update(id, subject);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        subjectService.delete(id);
    }

    @GetMapping("/{id}")
    public Subject selectOne(@PathVariable Integer id) {
        return subjectService.selectOne(id);
    }

}
