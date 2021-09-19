package com.geekbrains.ru.springmarket.repository;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByAlias(String alias);

    Set<CategoryEntity> findAllByParentCategoryIsNull();

}
