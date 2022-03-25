package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户仓库类
 *
 * @author zhangquanli
 * @since 2022/3/22 16:50:00
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
