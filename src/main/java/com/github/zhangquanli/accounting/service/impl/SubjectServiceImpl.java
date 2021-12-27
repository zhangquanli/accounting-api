package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.Subject;
import com.github.zhangquanli.accounting.exception.EntityNonExistException;
import com.github.zhangquanli.accounting.repository.SubjectRepository;
import com.github.zhangquanli.accounting.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 科目服务类
 *
 * @author zhangquanli
 * @since 2021/12/20 11:18:00
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepository subjectRepository;

    @Autowired
    public void setSubjectRepository(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> select() {
        return subjectRepository.findAll();
    }

    @Override
    public void insert(Subject subject) {
        subject.setId(null);
        subjectRepository.save(subject);
        String num = subject.getParentNum() + "-" + subject.getId();
        subject.setNum(num);
        subjectRepository.save(subject);
    }

    @Override
    public Subject selectOne(Integer id) {
        return subjectRepository.findById(id).orElseThrow(EntityNonExistException::new);
    }

    @Override
    public void update(Integer id, Subject subject) {
        if (!subjectRepository.existsById(id)) {
            throw new EntityNonExistException();
        }
        subject.setId(id);
        subjectRepository.save(subject);
    }

    @Override
    public void delete(Integer id) {
        subjectRepository.deleteById(id);
    }

}
