package com.geekbrains.ru.springmarket.controller.mvc;

import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.domain.dto.CategoryTree;
import com.geekbrains.ru.springmarket.service.CategoryService;
import com.geekbrains.ru.springmarket.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @MockBean
    private Validator validator;

    @Test
    void testDeleteProductById() throws Exception {
        doNothing().when(this.productService).deleteById(any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/product/delete");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/product/list"));
    }

    @Test
    void testGetProductFormEmpty() throws Exception {
        when(this.categoryService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/form");
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories", "product", "violations"))
                .andExpect(MockMvcResultMatchers.view().name("product/form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("product/form"));
    }

    @Test
    void testGetProductFormWithProduct() throws Exception {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setImageLink("Image Link");
        productEntity.setId(123L);
        productEntity.setPrice(10.0);
        productEntity.setTitle("Dr");
        productEntity.setCategories(new HashSet<>());
        when(this.productService.findById(anyLong())).thenReturn(productEntity);
        when(this.categoryService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/product/form");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories", "product", "violations"))
                .andExpect(MockMvcResultMatchers.view().name("product/form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("product/form"));
    }

    @Test
    void testGetProducts() throws Exception {
        when(this.productService.findAllByPageAndCategory(any(), any()))
                .thenReturn(new PageImpl<ProductEntity>(new ArrayList<ProductEntity>()));
        when(this.categoryService.getCategoryTree()).thenReturn(new CategoryTree());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/list");
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categoryTree", "page"))
                .andExpect(MockMvcResultMatchers.view().name("product/list"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("product/list"));
    }

    @Test
    void testGetProductsWithPagination() throws Exception {
        when(this.productService.findAllByPageAndCategory(any(), any()))
                .thenReturn(new PageImpl<ProductEntity>(new ArrayList<ProductEntity>()));
        when(this.categoryService.getCategoryTree()).thenReturn(new CategoryTree());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/product/list");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("pageNum", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categoryTree", "page"))
                .andExpect(MockMvcResultMatchers.view().name("product/list"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("product/list"));
    }

    @Test
    void testSaveProduct() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
}

