package com.geekbrains.ru.springmarket.service;

import com.geekbrains.ru.springmarket.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserEntity findByUsername(String username);

    UserEntity save(UserEntity user);

    Page<UserEntity> findAllByPage(Pageable pageRequest);

    void setEnable(Long userId, Boolean enable);

}
