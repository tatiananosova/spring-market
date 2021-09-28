package com.geekbrains.ru.springmarket.service.impl;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.domain.search.ProductSearchCondition;
import com.geekbrains.ru.springmarket.dto.ProductViewDto;
import com.geekbrains.ru.springmarket.repository.ProductRepository;
import com.geekbrains.ru.springmarket.service.CategoryService;
import com.geekbrains.ru.springmarket.service.ProductService;
import com.geekbrains.ru.springmarket.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public Page<ProductEntity> findAllByPageAndCategory(Pageable pageable, String categoryAlias) {
        if (StringUtils.isNotBlank(categoryAlias)) {
            CategoryEntity category = categoryService.findByAlias(categoryAlias);
            return productRepository.findAllByCategories(category, pageable);
        }

        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductEntity> findAllBySearchCondition(ProductSearchCondition searchCondition) {
        Pageable pageRequest = PageRequest.of(
                searchCondition.getPageNum(),
                searchCondition.getPageSize(),
                Sort.by(searchCondition.getSortDirection(), searchCondition.getSortField())
        );

        return productRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public ProductEntity saveWithImage(ProductEntity product, MultipartFile image) {
        ProductEntity savedProduct = productRepository.save(product);

        if (image != null && !image.isEmpty()) {
            Path pathImage = FileUtils.saveProductImage(image);
            savedProduct.setImageLink(pathImage.toString());

            productRepository.save(savedProduct);
        }

        return savedProduct;
    }

    @Override
    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

}
