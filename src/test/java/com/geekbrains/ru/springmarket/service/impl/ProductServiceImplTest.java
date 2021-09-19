package com.geekbrains.ru.springmarket.service.impl;

import com.geekbrains.ru.springmarket.SpringMvcDemoApplicationTest;
import com.geekbrains.ru.springmarket.repository.ProductRepository;
import com.geekbrains.ru.springmarket.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class ProductServiceImplTest extends SpringMvcDemoApplicationTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
//        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
//        productService = new ProductServiceImpl(productRepository);

//        Mockito.when(productRepository.findById(1L))
//                .thenReturn(java.util.Optional.of(new ProductEntity(1L, "test", 20)));
//
//        Mockito.when(productRepository.findById(2L))
//                .thenReturn(java.util.Optional.empty());
    }

//    @Test
//    void findAll() {
//        ProductEntity productEntity = new ProductEntity(1L, "title", 20);
//
//        ProductEntity savedProductEntity = productService.save(productEntity);
//        Mockito.verify(productRepository, Mockito.times(1)).save(productEntity);
//    }
//
//    @Test
//    void findByIdExist() {
//        ProductEntity expectedProductEntity = new ProductEntity(1L, "test", 20);
//
//        ProductEntity product = productService.findById(1L);
//
//        Assertions.assertEquals(expectedProductEntity, product);
//    }

//    @Test
//    void findByIdNotExist() {
//        ProductEntity product = productService.findById(2L);
//
//        Assertions.assertNull(product);
//    }
}