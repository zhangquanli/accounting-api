package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.User;
import com.github.zhangquanli.accounting.entity.base.UserRelRole;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.query.UserQuery;
import com.github.zhangquanli.accounting.repository.UserRepository;
import com.github.zhangquanli.accounting.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author zhangquanli
 * @since 2023/3/14
 */
@Transactional(rollbackFor = RuntimeException.class)
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ListResult<User> selectAll(UserQuery userQuery, PageableQuery pageableQuery) {
        Specification<User> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userQuery.getUsername() != null) {
                Predicate predicate = cb.like(root.get("username"), "%" + userQuery.getUsername() + "%");
                predicates.add(predicate);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageableQuery.toPageable(pageableQuery);
        Page<User> users = userRepository.findAll(specification, pageable);
        return ListResult.create(users);
    }

    @Override
    public User selectOne(Integer id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User selectByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void insert(User user) {
        // 关联的【角色】集合
        List<UserRelRole> userRelRoles = user.getUserRelRoles()
                .stream()
                .peek(userRelRole -> userRelRole.setUser(user))
                .collect(Collectors.toList());
        user.setUserRelRoles(userRelRoles);

        // 新增【用户】
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        // 关联的【角色】集合
        List<UserRelRole> userRelRoles = user.getUserRelRoles()
                .stream()
                .peek(userRelRole -> userRelRole.setUser(user))
                .collect(Collectors.toList());
        user.getUserRelRoles().clear();
        user.getUserRelRoles().addAll(userRelRoles);

        // 修改【用户】
        userRepository.save(user);
    }
}
