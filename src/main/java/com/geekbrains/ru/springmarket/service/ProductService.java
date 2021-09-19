package com.geekbrains.ru.springmarket.service;

import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.domain.search.ProductSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<ProductEntity> findAll();

    ProductEntity findById(long id);

    ProductEntity save(ProductEntity productEntity);

    Page<ProductEntity> findAllByPageAndCategory(Pageable pageable, String categoryAlias);

    Page<ProductEntity> findAllBySearchCondition(ProductSearchCondition searchCondition);

    ProductEntity saveWithImage(ProductEntity product, MultipartFile image);

    void deleteById(Long productId);
}
