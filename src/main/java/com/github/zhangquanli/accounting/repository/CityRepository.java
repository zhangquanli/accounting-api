package com.github.zhangquanli.accounting.repository;

import com.github.zhangquanli.accounting.entity.area.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByProvinceCode(String provinceCode);
}
