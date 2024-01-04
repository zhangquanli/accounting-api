package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.Role;
import com.github.zhangquanli.accounting.entity.base.RoleRelComponentInfo;
import com.github.zhangquanli.accounting.entity.base.RoleRelDisplayColumn;
import com.github.zhangquanli.accounting.entity.base.RoleRelPageInfo;
import com.github.zhangquanli.accounting.repository.RoleRepository;
import com.github.zhangquanli.accounting.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = RuntimeException.class)
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ListResult<Role> selectAll() {
        Specification<Role> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.isNull(root.get("parent"));
            predicates.add(predicate);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<Role> roles = roleRepository.findAll(specification);
        return ListResult.create(roles);
    }

    @Override
    public Role selectOne(Integer id) {
        return roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void insert(Role role) {
        // 关联的【页面】
        List<RoleRelPageInfo> pageInfos = role.getRoleRelPageInfos()
                .stream()
                .peek(roleRelPageInfo -> roleRelPageInfo.setRole(role))
                .collect(Collectors.toList());
        role.setRoleRelPageInfos(pageInfos);

        // 关联的【组件】
        List<RoleRelComponentInfo> componentInfos = role.getRoleRelComponentInfos()
                .stream()
                .peek(roleRelComponentInfo -> roleRelComponentInfo.setRole(role))
                .collect(Collectors.toList());
        role.setRoleRelComponentInfos(componentInfos);

        // 关联的【展示字段】
        List<RoleRelDisplayColumn> displayColumns = role.getRoleRelDisplayColumns()
                .stream()
                .peek(roleRelDisplayColumn -> roleRelDisplayColumn.setRole(role))
                .collect(Collectors.toList());
        role.setRoleRelDisplayColumns(displayColumns);

        // 保存【角色】
        roleRepository.save(role);
    }

    @Override
    public void update(Role role) {
        // 关联的【页面】
        List<RoleRelPageInfo> pageInfos = role.getRoleRelPageInfos()
                .stream()
                .peek(roleRelPageInfo -> roleRelPageInfo.setRole(role))
                .toList();
        role.getRoleRelPageInfos().clear();
        role.getRoleRelPageInfos().addAll(pageInfos);

        // 关联的【组件】
        List<RoleRelComponentInfo> componentInfos = role.getRoleRelComponentInfos()
                .stream()
                .peek(roleRelComponentInfo -> roleRelComponentInfo.setRole(role))
                .toList();
        role.getRoleRelComponentInfos().clear();
        role.getRoleRelComponentInfos().addAll(componentInfos);

        // 关联的【展示字段】
        List<RoleRelDisplayColumn> displayColumns = role.getRoleRelDisplayColumns()
                .stream()
                .peek(roleRelDisplayColumn -> roleRelDisplayColumn.setRole(role))
                .toList();
        role.getRoleRelDisplayColumns().clear();
        role.getRoleRelDisplayColumns().addAll(displayColumns);

        // 保存【角色】
        roleRepository.save(role);
    }
}
