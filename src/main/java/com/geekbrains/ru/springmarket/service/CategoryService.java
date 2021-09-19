package com.geekbrains.ru.springmarket.service;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import com.geekbrains.ru.springmarket.domain.dto.CategoryTree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<CategoryEntity> findAll();

    CategoryEntity findById(Long id);

    CategoryEntity findByAlias(String alias);

    CategoryEntity save(CategoryEntity category);

    Page<CategoryEntity> findAllByPage(Pageable pageable);

    CategoryTree getCategoryTree();
}
