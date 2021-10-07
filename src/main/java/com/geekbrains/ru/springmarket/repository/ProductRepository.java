package com.geekbrains.ru.springmarket.repository;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import com.geekbrains.ru.springmarket.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Page<ProductEntity> findAllByCategories(CategoryEntity category, Pageable pageable);

    ProductEntity findNativeProduct();

    Page<ProductEntity> findAll(Pageable pageRequest);

    ProductEntity save(ProductEntity product);

    void deleteById(Long productId);

    Optional<ProductEntity> findById(long id);

    Optional<ProductEntity> findByTitle(String title);

    List<ProductEntity> findAll();

//    Page<ProductEntity> findAllByTitleLikeIgnoreCase(String like, Pageable pageable);

}
