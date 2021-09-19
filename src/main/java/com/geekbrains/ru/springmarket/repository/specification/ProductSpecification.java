package com.geekbrains.ru.springmarket.repository.specification;

import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.domain.search.ProductSearchCondition;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProductSpecification implements Specification<ProductEntity> {

    private ProductSearchCondition searchCondition;

    // select * from product where title like ...
    @Override
    public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        if (StringUtils.isNotBlank(searchCondition.getTitleFilter())) {
            predicateList.add(criteriaBuilder.like(root.get("title"), searchCondition.getTitleFilter()));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

}
