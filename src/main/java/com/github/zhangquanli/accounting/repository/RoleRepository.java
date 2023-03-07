package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.base.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
