package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.base.Role;
import com.github.zhangquanli.accounting.entity.base.RoleRelComponentInfo;
import com.github.zhangquanli.accounting.entity.base.RoleRelDisplayColumn;
import com.github.zhangquanli.accounting.entity.base.RoleRelPageInfo;
import com.github.zhangquanli.accounting.repository.RoleRepository;
import com.github.zhangquanli.accounting.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> selectList() {
        Specification<Role> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.isNull(root.get("parent"));
            predicates.add(predicate);
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return roleRepository.findAll(specification);
    }

    @Override
    public void insert(Role role) {
        // 关联的【页面】
        List<RoleRelPageInfo> pageInfos = role.getPageInfos()
                .stream()
                .peek(roleRelPageInfo -> roleRelPageInfo.setRole(role))
                .collect(Collectors.toList());
        role.setPageInfos(pageInfos);

        // 关联的【组件】
        List<RoleRelComponentInfo> componentInfos = role.getComponentInfos()
                .stream()
                .peek(roleRelComponentInfo -> roleRelComponentInfo.setRole(role))
                .collect(Collectors.toList());
        role.setComponentInfos(componentInfos);

        // 关联的【展示字段】
        List<RoleRelDisplayColumn> displayColumns = role.getDisplayColumns()
                .stream()
                .peek(roleRelDisplayColumn -> roleRelDisplayColumn.setRole(role))
                .collect(Collectors.toList());
        role.setDisplayColumns(displayColumns);

        // 保存【角色】
        roleRepository.save(role);
    }

    @Override
    public void update(Role role) {
        // 关联的【页面】
        List<RoleRelPageInfo> pageInfos = role.getPageInfos()
                .stream()
                .peek(roleRelPageInfo -> roleRelPageInfo.setRole(role))
                .collect(Collectors.toList());
        role.getPageInfos().clear();
        role.getPageInfos().addAll(pageInfos);

        // 关联的【组件】
        List<RoleRelComponentInfo> componentInfos = role.getComponentInfos()
                .stream()
                .peek(roleRelComponentInfo -> roleRelComponentInfo.setRole(role))
                .collect(Collectors.toList());
        role.getComponentInfos().clear();
        role.getComponentInfos().addAll(componentInfos);

        // 关联的【展示字段】
        List<RoleRelDisplayColumn> displayColumns = role.getDisplayColumns()
                .stream()
                .peek(roleRelDisplayColumn -> roleRelDisplayColumn.setRole(role))
                .collect(Collectors.toList());
        role.getDisplayColumns().clear();
        role.getDisplayColumns().addAll(displayColumns);

        // 保存【角色】
        roleRepository.save(role);
    }
}
