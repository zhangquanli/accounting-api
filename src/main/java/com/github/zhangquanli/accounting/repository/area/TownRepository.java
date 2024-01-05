package com.github.zhangquanli.accounting.repository.area;

import com.github.zhangquanli.accounting.entity.area.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownRepository extends JpaRepository<Town, Integer> {
    List<Town> findByDistrictContent(String districtContent);
}
