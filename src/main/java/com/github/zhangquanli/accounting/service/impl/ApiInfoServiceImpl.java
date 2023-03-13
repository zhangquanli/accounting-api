package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.base.ApiInfo;
import com.github.zhangquanli.accounting.query.ApiInfoQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.repository.ApiInfoRepository;
import com.github.zhangquanli.accounting.service.ApiInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiInfoServiceImpl implements ApiInfoService {
    private final ApiInfoRepository apiInfoRepository;

    public ApiInfoServiceImpl(ApiInfoRepository apiInfoRepository) {
        this.apiInfoRepository = apiInfoRepository;
    }

    @Override
    public Page<ApiInfo> selectPage(ApiInfoQuery apiInfoQuery, PageableQuery pageableQuery) {
        Specification<ApiInfo> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (apiInfoQuery.getName() != null) {
                Predicate predicate = cb.like(root.get("name"), "%" + apiInfoQuery.getName() + "%");
                predicates.add(predicate);
            }
            if (apiInfoQuery.getUrl() != null) {
                Predicate predicate = cb.like(root.get("url"), "%" + apiInfoQuery.getName() + "%");
                predicates.add(predicate);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        PageRequest pageRequest = PageRequest.of(pageableQuery.getPage() - 1,
                pageableQuery.getSize());
        return apiInfoRepository.findAll(specification, pageRequest);
    }

    @Override
    public void insert(ApiInfo apiInfo) {
        apiInfoRepository.save(apiInfo);
    }

    @Override
    public void update(ApiInfo apiInfo) {
        if (!apiInfoRepository.existsById(apiInfo.getId())) {
            throw new EntityNotFoundException();
        }
        apiInfoRepository.save(apiInfo);
    }
}
