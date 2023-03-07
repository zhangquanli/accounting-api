package com.github.zhangquanli.accounting.service.impl;

import com.github.zhangquanli.accounting.entity.base.Api;
import com.github.zhangquanli.accounting.query.ApiQuery;
import com.github.zhangquanli.accounting.query.PageableQuery;
import com.github.zhangquanli.accounting.repository.ApiRepository;
import com.github.zhangquanli.accounting.service.ApiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {
    private final ApiRepository apiRepository;

    public ApiServiceImpl(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Override
    public Page<Api> selectPage(ApiQuery apiQuery, PageableQuery pageableQuery) {
        Specification<Api> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (apiQuery.getName() != null) {
                Predicate predicate = cb.like(root.get("name"), "%" + apiQuery.getName() + "%");
                predicates.add(predicate);
            }
            if (apiQuery.getUrl() != null) {
                Predicate predicate = cb.like(root.get("url"), "%" + apiQuery.getName() + "%");
                predicates.add(predicate);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        PageRequest pageRequest = PageRequest.of(pageableQuery.getCurrent() - 1,
                pageableQuery.getPageSize());
        return apiRepository.findAll(specification, pageRequest);
    }

    @Override
    public void insert(Api api) {
        apiRepository.save(api);
    }

    @Override
    public void update(Api api) {
        if (!apiRepository.existsById(api.getId())) {
            throw new EntityNotFoundException();
        }
        apiRepository.save(api);
    }
}
