package com.geekbrains.ru.springmarket.controller.mvc;

import com.geekbrains.ru.springmarket.domain.UserEntity;
import com.geekbrains.ru.springmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String getUsers(@RequestParam(required = false) Integer pageNum, Model model) {
        final int pageSize = 5;

        Pageable pageRequest = PageRequest.of(pageNum == null ? 0 : pageNum, pageSize);
        Page<UserEntity> page = userService.findAllByPage(pageRequest);

        model.addAttribute("page", page);

        return "user/list";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("user", new UserEntity());

        return "user/registration";
    }

    @PostMapping("/registration")
    public String register(@Valid UserEntity user) {
        userService.save(user);

        return "redirect:/login";
    }

    @GetMapping("/enable")
    public String setEnableUser(@RequestParam Long userId, @RequestParam Boolean enable) {
        userService.setEnable(userId, enable);

        return "redirect:/user/list";
    }

}
