package com.geekbrains.ru.springmarket.controller.mvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.geekbrains.ru.springmarket.domain.UserEntity;
import com.geekbrains.ru.springmarket.service.UserService;

import java.util.ArrayList;

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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void testGetRegistration() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/registration");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("user/registration"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/registration"));
    }

    @Test
    void testGetRegistrationWithUriVars() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/registration", "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("user/registration"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/registration"));
    }

    @Test
    void testGetUsers() throws Exception {
        when(this.userService.findAllByPage((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<UserEntity>(new ArrayList<UserEntity>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/list");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("page"))
                .andExpect(MockMvcResultMatchers.view().name("user/list"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/list"));
    }

    @Test
    void testGetUsersWithPagination() throws Exception {
        when(this.userService.findAllByPage((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<UserEntity>(new ArrayList<UserEntity>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/list");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("pageNum", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("page"))
                .andExpect(MockMvcResultMatchers.view().name("user/list"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/list"));
    }

    @Test
    void testRegister() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/registration");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testSetEnableUser() throws Exception {
        doNothing().when(this.userService).setEnable((Long) any(), (Boolean) any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/enable");
        MockHttpServletRequestBuilder paramResult = getResult.param("enable", String.valueOf(true));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("userId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/user/list"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }
}

