package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.base.PermissionColumn;
import com.github.zhangquanli.accounting.repository.PermissionColumnRepository;
import com.github.zhangquanli.accounting.service.PermissionColumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionColumServiceImpl implements PermissionColumService {
    private PermissionColumnRepository permissionColumnRepository;

    @Autowired
    public void setPermissionColumnRepository(PermissionColumnRepository permissionColumnRepository) {
        this.permissionColumnRepository = permissionColumnRepository;
    }

    @Override
    public List<PermissionColumn> selectList() {
        Specification<PermissionColumn> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.isNull(root.get("parent"));
            predicates.add(predicate);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return permissionColumnRepository.findAll(specification);
    }

    @Override
    public PermissionColumn selectOne(Integer id) {
        return permissionColumnRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
