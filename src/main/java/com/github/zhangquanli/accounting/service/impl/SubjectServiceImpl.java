package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.Subject;
import com.github.zhangquanli.accounting.query.SubjectQuery;
import com.github.zhangquanli.accounting.repository.SubjectRepository;
import com.github.zhangquanli.accounting.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 科目服务类
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> selectList(SubjectQuery subjectQuery) {
        Specification<Subject> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 代码
            if (subjectQuery.getCode() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("code"),
                        "%" + subjectQuery.getCode() + "%");
                predicates.add(predicate);
            }
            // 名称
            if (subjectQuery.getName() != null) {
                Predicate predicate = criteriaBuilder.like(root.get("name"),
                        "%" + subjectQuery.getName() + "%");
                predicates.add(predicate);
            }
            // 分类
            if (subjectQuery.getCategory() != null) {
                Predicate predicate = criteriaBuilder.equal(root.get("category"), subjectQuery.getCategory());
                predicates.add(predicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return subjectRepository.findAll(specification);
    }

    @Override
    public void insert(Subject subject) {
        subjectRepository.save(subject);
        String num = subject.getParentNum() + "-" + subject.getId();
        subject.setNum(num);
        subjectRepository.save(subject);
    }

    @Override
    public void update(Subject newSubject) {
        Subject subject = subjectRepository.findById(newSubject.getId()).orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(newSubject, subject);
        subjectRepository.save(subject);
    }

    @Override
    public Subject selectOne(Integer id) {
        return subjectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
