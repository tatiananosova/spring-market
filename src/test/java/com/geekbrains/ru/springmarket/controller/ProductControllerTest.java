package com.geekbrains.ru.springmarket.controller;

import com.geekbrains.ru.springmarket.SpringMvcDemoApplicationTest;
import com.geekbrains.ru.springmarket.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest extends SpringMvcDemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

//    @BeforeEach
//    void setUp() {
//        ProductEntity productEntity = new ProductEntity(1L, "title", 20);
//
//        Mockito.when(productService.findAll())
//                .thenReturn(Collections.singletonList(productEntity));
//    }
//
//    @Test
//    void getProducts() throws Exception {
//        ProductEntity productEntity = new ProductEntity(1L, "title", 20);
//
//        mockMvc.perform(get("/product"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("products"))
//                .andExpect(model().attribute("products", Collections.singletonList(productEntity)));
//
//    }
//
//    @Test
//    void correctAddProducts() throws Exception {
//        ProductEntity productEntity = new ProductEntity(1L, "title", 20);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/product", productEntity))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/product"));
//
//        Mockito.verify(productService, Mockito.times(1)).save(productEntity);
//    }

    @Test
    void addProductsWithoutId() throws Exception {
        mockMvc.perform(post("/product")
                        .param("name", "title")
                        .param("price", "20"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    void addProductsWithoutName() throws Exception {
        mockMvc.perform(post("/product")
                        .param("id", "1")
                        .param("name", "")
                        .param("price", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/add"))
                .andExpect(flash().attribute("error", "Name не может быть пустым"));
    }

}