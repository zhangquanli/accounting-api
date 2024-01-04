package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Subject;
import com.github.zhangquanli.accounting.query.SubjectQuery;

import java.util.List;

/**
 * 科目服务类
 */
public interface SubjectService {
    List<Subject> selectList(SubjectQuery subjectQuery);

    void insert(Subject subject);

    void update(Subject subject);

    Subject selectOne(Integer id);
}
