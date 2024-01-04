package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 标签仓库类
 */
public interface LabelRepository extends JpaRepository<Label, Integer> {
    Optional<Label> findByMark(String mark);
}
