package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.base.Role;
import com.github.zhangquanli.accounting.repository.RoleRepository;
import com.github.zhangquanli.accounting.service.RoleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> selectList() {
        return roleRepository.findAll();
    }

    @Override
    public void update(Role newRole) {
        Role role = roleRepository.findById(newRole.getId()).orElseThrow(EntityNotFoundException::new);

//        // 删除原有数据
//        role.setPages(Collections.emptySet());
//        role.setComponents(Collections.emptySet());
//        role.setColumns(Collections.emptySet());
//        roleRepository.save(role);

        //
        role.setPages(newRole.getPages());
        role.setComponents(newRole.getComponents());
        role.setColumns(newRole.getColumns());
        roleRepository.save(role);
    }
}
