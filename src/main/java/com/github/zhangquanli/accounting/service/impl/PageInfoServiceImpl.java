package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.BaseEntity;
import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.entity.base.ComponentInfo;
import com.github.zhangquanli.accounting.entity.base.DisplayColumn;
import com.github.zhangquanli.accounting.entity.base.PageInfo;
import com.github.zhangquanli.accounting.repository.ApiInfoRepository;
import com.github.zhangquanli.accounting.repository.PageInfoRepository;
import com.github.zhangquanli.accounting.service.PageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageInfoServiceImpl implements PageInfoService {
    private PageInfoRepository pageInfoRepository;
    private ApiInfoRepository apiInfoRepository;

    @Autowired
    public void setPageRepository(PageInfoRepository pageInfoRepository) {
        this.pageInfoRepository = pageInfoRepository;
    }

    @Autowired
    public void setApiRepository(ApiInfoRepository apiInfoRepository) {
        this.apiInfoRepository = apiInfoRepository;
    }

    @Override
    public List<PageInfo> selectList() {
        Specification<PageInfo> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.isNull(root.get("parent"));
            predicates.add(predicate);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Sort sort = Sort.by(Sort.Order.desc("type"));
        return pageInfoRepository.findAll(specification, sort);
    }

    @Override
    public List<ApiInfo> selectApis() {
        return apiInfoRepository.findAll();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void insert(PageInfo pageInfo) {
        // 关联的【组件】集合
        List<ComponentInfo> componentInfos = pageInfo.getComponentInfos()
                .parallelStream()
                .peek(componentInfo -> {
                    processComponent(componentInfo);
                    componentInfo.setPageInfo(pageInfo);
                }).collect(Collectors.toList());
        pageInfo.getComponentInfos().addAll(componentInfos);

        // 新增【页面】
        pageInfoRepository.save(pageInfo);
    }

    @Override
    public void update(PageInfo pageInfo) {
        // 关联的【接口】集合
        List<ApiInfo> apiInfos = pageInfo.getApiInfos();
        pageInfo.getApiInfos().clear();
        pageInfo.getApiInfos().addAll(apiInfos);

        // 关联的【组件】集合
        List<ComponentInfo> componentInfos = pageInfo.getComponentInfos().stream()
                .peek(componentInfo -> {
                    processComponent(componentInfo);
                    componentInfo.setPageInfo(pageInfo);
                })
                .collect(Collectors.toList());
        pageInfo.getComponentInfos().clear();
        pageInfo.getComponentInfos().addAll(componentInfos);

        // 修改【页面】
        pageInfoRepository.save(pageInfo);
    }

    private void processComponent(ComponentInfo componentInfo) {
        // 关联的【接口】集合
        List<Integer> apiIds = componentInfo.getApiInfos().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        List<ApiInfo> apiInfos = apiInfoRepository.findAllById(apiIds);
        componentInfo.getApiInfos().clear();
        componentInfo.getApiInfos().addAll(apiInfos);

        // 关联的【展示字段】集合
        List<DisplayColumn> displayColumns = componentInfo.getDisplayColumns().parallelStream()
                .peek(displayColumn -> displayColumn.setComponentInfo(componentInfo))
                .collect(Collectors.toList());
        componentInfo.getDisplayColumns().clear();
        componentInfo.getDisplayColumns().addAll(displayColumns);
    }
}
