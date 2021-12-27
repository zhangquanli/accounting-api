package com.github.zhangquanli.accounting.service;

import com.github.zhangquanli.accounting.entity.Subject;

import java.util.List;

/**
 * 科目服务类
 *
 * @author zhangquanli
 * @since 2021/12/20 11:18:00
 */
public interface SubjectService {
    List<Subject> select();

    void insert(Subject subject);

    Subject selectOne(Integer id);

    void update(Integer id, Subject subject);

    void delete(Integer id);
}
