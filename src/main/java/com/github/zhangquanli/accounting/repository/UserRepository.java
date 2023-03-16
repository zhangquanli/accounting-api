package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户仓库类
 *
 * @author zhangquanli
 * @since 2022/3/22 16:50:00
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
}
