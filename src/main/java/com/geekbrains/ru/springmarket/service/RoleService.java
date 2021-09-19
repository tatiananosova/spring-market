package com.geekbrains.ru.springmarket.service;

import com.geekbrains.ru.springmarket.domain.RoleEntity;

public interface RoleService {

    RoleEntity findByName(String name);

}
