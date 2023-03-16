package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.UserQuery;
import com.github.zhangquanli.accounting.repository.UserRepository;
import com.github.zhangquanli.accounting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 *
 * @author zhangquanli
 * @since 2023/3/14
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> selectPage(UserQuery userQuery, PageableQuery pageableQuery) {
        Specification<User> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.like(root.get("username"), "%" + userQuery.getUsername() + "%");
            predicates.add(predicate);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        PageRequest pageRequest = PageRequest.of(pageableQuery.getPage() - 1, pageableQuery.getSize());
        return userRepository.findAll(specification, pageRequest);
    }

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}
