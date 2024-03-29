package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.*;
import com.github.zhangquanli.accounting.repository.PageInfoRepository;
import com.github.zhangquanli.accounting.service.PageInfoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = RuntimeException.class)
@Service
public class PageInfoServiceImpl implements PageInfoService {
    private final PageInfoRepository pageInfoRepository;

    public PageInfoServiceImpl(PageInfoRepository pageInfoRepository) {
        this.pageInfoRepository = pageInfoRepository;
    }

    @Override
    public ListResult<PageInfo> selectAll() {
        Specification<PageInfo> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.isNull(root.get("parent"));
            predicates.add(predicate);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Sort sort = Sort.by(Sort.Order.desc("type"));
        List<PageInfo> pageInfos = pageInfoRepository.findAll(specification, sort);
        return ListResult.create(pageInfos);
    }

    @Override
    public PageInfo selectOne(Integer id) {
        return pageInfoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void insert(PageInfo pageInfo) {
        // 关联的【组件】集合
        List<ComponentInfo> componentInfos = pageInfo.getComponentInfos()
                .stream()
                .peek(componentInfo -> {
                    processComponent(componentInfo);
                    componentInfo.setPageInfo(pageInfo);
                }).collect(Collectors.toList());
        pageInfo.setComponentInfos(componentInfos);

        // 新增【页面】
        pageInfoRepository.save(pageInfo);
    }

    @Override
    public void update(PageInfo pageInfo) {
        // 关联的【权限字段】集合
        List<PermissionColumn> permissionColumns = new ArrayList<>(pageInfo.getPermissionColumns());
        pageInfo.getPermissionColumns().clear();
        pageInfo.getPermissionColumns().addAll(permissionColumns);

        // 关联的【接口】集合
        List<ApiInfo> apiInfos = new ArrayList<>(pageInfo.getApiInfos());
        pageInfo.getApiInfos().clear();
        pageInfo.getApiInfos().addAll(apiInfos);

        // 关联的【组件】集合
        List<ComponentInfo> componentInfos = pageInfo.getComponentInfos()
                .stream()
                .peek(componentInfo -> {
                    processComponent(componentInfo);
                    componentInfo.setPageInfo(pageInfo);
                })
                .toList();
        pageInfo.getComponentInfos().clear();
        pageInfo.getComponentInfos().addAll(componentInfos);

        // 修改【页面】
        pageInfoRepository.save(pageInfo);
    }

    private void processComponent(ComponentInfo componentInfo) {
        // 关联的【接口】集合
        List<ApiInfo> apiInfos = new ArrayList<>(componentInfo.getApiInfos());
        componentInfo.getApiInfos().clear();
        componentInfo.getApiInfos().addAll(apiInfos);

        // 关联的【展示字段】集合
        List<DisplayColumn> displayColumns = componentInfo.getDisplayColumns()
                .stream()
                .peek(displayColumn -> displayColumn.setComponentInfo(componentInfo))
                .toList();
        componentInfo.getDisplayColumns().clear();
        componentInfo.getDisplayColumns().addAll(displayColumns);
    }
}
