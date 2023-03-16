package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.area.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findByCityCode(String cityCode);
}
