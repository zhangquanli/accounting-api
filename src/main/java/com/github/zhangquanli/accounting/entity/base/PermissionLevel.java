package com.github.zhangquanli.accounting.entity.base;

import com.github.zhangquanli.accounting.config.SpringContextHolder;
import com.github.zhangquanli.accounting.entity.area.City;
import com.github.zhangquanli.accounting.entity.area.District;
import com.github.zhangquanli.accounting.entity.area.Province;
import com.github.zhangquanli.accounting.entity.area.Town;
import com.github.zhangquanli.accounting.repository.area.CityRepository;
import com.github.zhangquanli.accounting.repository.area.DistrictRepository;
import com.github.zhangquanli.accounting.repository.area.ProvinceRepository;
import com.github.zhangquanli.accounting.repository.area.TownRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 权限等级
 */
public enum PermissionLevel {
    ALL("全部", "", null) {
        @Override
        public List<OptionType> getOptions(String parentCode) {
            OptionType optionType = new OptionType();
            optionType.setValue("0");
            optionType.setLabel("全部");
            OptionType.Level level = OptionType.Level.builder()
                    .value(this.toString()).label(this.getName()).build();
            optionType.setLevel(level);
            return Collections.singletonList(optionType);
        }
    },
    PROVINCE_AGENT("省级代理", "agent_id", ALL) {
        @Override
        public List<OptionType> getOptions(String parentCode) {
            return Collections.emptyList();
        }
    },
    PROVINCE("省级", "province_id", ALL) {
        @Override
        public List<OptionType> getOptions(String parentCode) {
            ProvinceRepository repository = SpringContextHolder.getBean(ProvinceRepository.class);
            List<Province> provinces = repository.findAll();
            return provinces.stream().map(province -> {
                OptionType optionType = new OptionType();
                optionType.setValue(province.getContent());
                optionType.setLabel(province.getName());
                OptionType.Level level = OptionType.Level.builder()
                        .value(this.toString()).label(this.getName()).build();
                optionType.setLevel(level);
                return optionType;
            }).collect(Collectors.toList());
        }
    },
    CITY("市级", "city_id", PermissionLevel.PROVINCE) {
        @Override
        public List<OptionType> getOptions(String parentCode) {
            CityRepository repository = SpringContextHolder.getBean(CityRepository.class);
            List<City> cities = repository.findByProvinceContent(parentCode);
            return cities.stream().map(city -> {
                OptionType optionType = new OptionType();
                optionType.setValue(city.getContent());
                optionType.setLabel(city.getName());
                OptionType.Level level = OptionType.Level.builder()
                        .value(this.toString()).label(this.getName()).build();
                optionType.setLevel(level);
                return optionType;
            }).collect(Collectors.toList());

        }
    },
    DISTRICT("区县级", "district_id", PermissionLevel.CITY) {
        @Override
        public List<OptionType> getOptions(String parentCode) {
            DistrictRepository repository = SpringContextHolder.getBean(DistrictRepository.class);
            List<District> districts = repository.findByCityContent(parentCode);
            return districts.stream().map(district -> {
                OptionType optionType = new OptionType();
                optionType.setValue(district.getContent());
                optionType.setLabel(district.getName());
                OptionType.Level level = OptionType.Level.builder()
                        .value(this.toString()).label(this.getName()).build();
                optionType.setLevel(level);
                return optionType;
            }).collect(Collectors.toList());
        }
    },
    TOWN("乡镇级", "town_id", PermissionLevel.DISTRICT) {
        @Override
        public List<OptionType> getOptions(String parentCode) {
            TownRepository repository = SpringContextHolder.getBean(TownRepository.class);
            List<Town> towns = repository.findByDistrictContent(parentCode);
            return towns.stream().map(town -> {
                OptionType optionType = new OptionType();
                optionType.setValue(town.getContent());
                optionType.setLabel(town.getName());
                OptionType.Level level = OptionType.Level.builder()
                        .value(this.toString()).label(this.getName()).build();
                optionType.setLevel(level);
                return optionType;
            }).collect(Collectors.toList());
        }
    },
    ;

    private final String name;
    private final String code;
    private final PermissionLevel parent;

    PermissionLevel(String name, String code, PermissionLevel parent) {
        this.name = name;
        this.code = code;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public PermissionLevel getParent() {
        return parent;
    }

    public List<PermissionLevelWrapper> getChildren() {
        PermissionLevel current = this;
        Map<PermissionLevel, List<PermissionLevel>> map = Stream.of(PermissionLevel.values())
                .collect(Collectors.groupingBy(PermissionLevel::getParent));
        List<PermissionLevel> permissionLevels = map.get(current);
        return toTree(map, permissionLevels);
    }

    private List<PermissionLevelWrapper> toTree(
            Map<PermissionLevel, List<PermissionLevel>> map,
            List<PermissionLevel> permissionLevels) {
        return permissionLevels.stream()
                .map(permissionLevel -> {
                    PermissionLevelWrapper permissionLevelWrapper = new PermissionLevelWrapper();
                    permissionLevelWrapper.setCurrent(permissionLevel);
                    List<PermissionLevel> children = map.get(permissionLevel);
                    if (children != null && children.size() > 0) {
                        permissionLevelWrapper.setChildren(toTree(map, children));
                    } else {
                        permissionLevelWrapper.setChildren(Collections.emptyList());
                    }
                    return permissionLevelWrapper;
                }).collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class PermissionLevelWrapper {
        private PermissionLevel current;
        private List<PermissionLevelWrapper> children;
    }

    public abstract List<OptionType> getOptions(String parentCode);
}
