package com.geekbrains.ru.springmarket.service.impl;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import com.geekbrains.ru.springmarket.domain.dto.CategoryTree;
import com.geekbrains.ru.springmarket.repository.CategoryRepository;
import com.geekbrains.ru.springmarket.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CategoryEntity findByAlias(String alias) {
        return categoryRepository.findByAlias(alias).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public Page<CategoryEntity> findAllByPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public CategoryTree getCategoryTree() {
        Set<CategoryEntity> categories = categoryRepository.findAllByParentCategoryIsNull();
        List<CategoryTree.TreeEntry> rootEntries = convertToTreeEntries(categories);

        return CategoryTree.builder()
                .rootCategories(rootEntries)
                .build();
    }

    private List<CategoryTree.TreeEntry> convertToTreeEntries(Set<CategoryEntity> rootCategories) {
        if (CollectionUtils.isEmpty(rootCategories)) return Collections.emptyList();

        return rootCategories.stream()
                .map(this::convertToTreeEntry)
                .collect(Collectors.toList());
    }

    private CategoryTree.TreeEntry convertToTreeEntry(CategoryEntity category) {
        return CategoryTree.TreeEntry.builder()
                .category(category)
                .subCategories(convertToTreeEntries(category.getSubCategories()))
                .build();
    }

}
