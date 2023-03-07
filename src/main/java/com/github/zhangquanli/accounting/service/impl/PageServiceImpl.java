package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.base.Api;
import com.github.zhangquanli.accounting.entity.base.Component;
import com.github.zhangquanli.accounting.entity.base.DataColumn;
import com.github.zhangquanli.accounting.entity.base.Page;
import com.github.zhangquanli.accounting.repository.ApiRepository;
import com.github.zhangquanli.accounting.repository.PageRepository;
import com.github.zhangquanli.accounting.service.PageService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PageServiceImpl implements PageService {
    private PageRepository pageRepository;
    private ApiRepository apiRepository;

    public void setPageRepository(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public void setApiRepository(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Override
    public List<Page> selectList() {
        Specification<Page> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.isNull(root.get("parent"));
            predicates.add(predicate);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return pageRepository.findAll(specification);
    }

    @Override
    public void insert(Page page) {
        // 关联的【接口】集合
        Set<Api> apis = page.getApis().parallelStream()
                .filter(api -> apiRepository.existsById(api.getId()))
                .collect(Collectors.toSet());
        page.setApis(apis);

        // 关联的【组件】集合
        Set<Component> components = page.getComponents().parallelStream()
                .peek(component -> {
                    processComponent(component);
                    component.setPage(page);
                }).collect(Collectors.toSet());
        page.setComponents(components);

        // 新增【页面】
        pageRepository.save(page);
    }

    private void processComponent(Component component) {
        // 关联的【接口】集合
        Set<Api> apis = component.getApis().parallelStream()
                .filter(api -> apiRepository.existsById(api.getId()))
                .collect(Collectors.toSet());
        component.setApis(apis);

        // 关联的【数据字段】集合
        Set<DataColumn> dataColumns = component.getDataColumns().parallelStream()
                .peek(dataColumn -> dataColumn.setComponent(component))
                .collect(Collectors.toSet());
        component.setDataColumns(dataColumns);
    }

    @Override
    public void update(Page page) {
        // 关联的【接口】集合
        
    }
}
