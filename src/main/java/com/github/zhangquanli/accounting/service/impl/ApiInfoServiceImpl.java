package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.ListResult;
import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.query.ApiInfoQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.repository.ApiInfoRepository;
import com.github.zhangquanli.accounting.service.ApiInfoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = RuntimeException.class)
@Service
public class ApiInfoServiceImpl implements ApiInfoService {
    private final ApiInfoRepository apiInfoRepository;

    public ApiInfoServiceImpl(ApiInfoRepository apiInfoRepository) {
        this.apiInfoRepository = apiInfoRepository;
    }

    @Override
    public ListResult<ApiInfo> selectAll(ApiInfoQuery apiInfoQuery, PageableQuery pageableQuery) {
        Specification<ApiInfo> specification = toSpecification(apiInfoQuery);
        if (PageableQuery.pageable(pageableQuery)) {
            Pageable pageable = PageableQuery.toPageable(pageableQuery);
            Page<ApiInfo> apiInfos = apiInfoRepository.findAll(specification, pageable);
            return ListResult.create(apiInfos);
        } else {
            List<ApiInfo> apiInfos = apiInfoRepository.findAll(specification);
            return ListResult.create(apiInfos);
        }
    }

    @Override
    public ApiInfo selectOne(Integer id) {
        return apiInfoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void insert(ApiInfo apiInfo) {
        apiInfoRepository.save(apiInfo);
    }

    @Override
    public void update(ApiInfo apiInfo) {
        apiInfoRepository.save(apiInfo);
    }

    private Specification<ApiInfo> toSpecification(ApiInfoQuery apiInfoQuery) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (apiInfoQuery.getName() != null) {
                Predicate predicate = cb.like(root.get("name"), "%" + apiInfoQuery.getName() + "%");
                predicates.add(predicate);
            }
            if (apiInfoQuery.getUrl() != null) {
                Predicate predicate = cb.like(root.get("url"), "%" + apiInfoQuery.getUrl() + "%");
                predicates.add(predicate);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
