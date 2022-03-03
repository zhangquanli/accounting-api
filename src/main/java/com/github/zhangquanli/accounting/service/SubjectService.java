package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Subject;
import com.github.zhangquanli.accounting.query.SubjectQuery;

import java.util.List;

/**
 * 科目服务类
 *
 * @author zhangquanli
 * @since 2021/12/20 11:18:00
 */
public interface SubjectService {
    List<Subject> select(SubjectQuery subjectQuery);

    void insert(Subject subject);

    void update(Integer id, Subject subject);

    void delete(Integer id);

    Subject selectOne(Integer id);
}
