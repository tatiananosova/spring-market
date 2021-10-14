package com.geekbrains.ru.springmarket.controller.mvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.service.CategoryService;

import java.util.ArrayList;
import java.util.HashSet;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CategoryController.class})
@ExtendWith(SpringExtension.class)
class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private Validator validator;

    @Test
    void testGetCategoryFormEmpty() throws Exception {
        when(this.categoryService.findAll()).thenReturn(new ArrayList<CategoryEntity>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/form");
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories", "category", "violations"))
                .andExpect(MockMvcResultMatchers.view().name("category/form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("category/form"));
    }

    @Test
    void testGetCategoryFormWithCategory() throws Exception {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentCategory(new CategoryEntity());
        categoryEntity.setSubCategories(new HashSet<CategoryEntity>());
        categoryEntity.setId(123L);
        categoryEntity.setName("Name");
        categoryEntity.setAlias("Alias");
        categoryEntity.setProducts(new HashSet<ProductEntity>());

        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setParentCategory(categoryEntity);
        categoryEntity1.setSubCategories(new HashSet<CategoryEntity>());
        categoryEntity1.setId(123L);
        categoryEntity1.setName("Name");
        categoryEntity1.setAlias("Alias");
        categoryEntity1.setProducts(new HashSet<ProductEntity>());

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setParentCategory(categoryEntity1);
        categoryEntity2.setSubCategories(new HashSet<CategoryEntity>());
        categoryEntity2.setId(123L);
        categoryEntity2.setName("Name");
        categoryEntity2.setAlias("Alias");
        categoryEntity2.setProducts(new HashSet<ProductEntity>());

        CategoryEntity categoryEntity3 = new CategoryEntity();
        categoryEntity3.setParentCategory(categoryEntity2);
        categoryEntity3.setSubCategories(new HashSet<CategoryEntity>());
        categoryEntity3.setId(123L);
        categoryEntity3.setName("Name");
        categoryEntity3.setAlias("Alias");
        categoryEntity3.setProducts(new HashSet<ProductEntity>());

        CategoryEntity categoryEntity4 = new CategoryEntity();
        categoryEntity4.setParentCategory(categoryEntity3);
        categoryEntity4.setSubCategories(new HashSet<CategoryEntity>());
        categoryEntity4.setId(123L);
        categoryEntity4.setName("Name");
        categoryEntity4.setAlias("Alias");
        categoryEntity4.setProducts(new HashSet<ProductEntity>());
        when(this.categoryService.findById((Long) any())).thenReturn(categoryEntity4);
        when(this.categoryService.findAll()).thenReturn(new ArrayList<CategoryEntity>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/category/form");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories", "category", "violations"))
                .andExpect(MockMvcResultMatchers.view().name("category/form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("category/form"));
    }

    @Test
    void testSaveCategory() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
}

