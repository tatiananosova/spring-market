package com.geekbrains.ru.springmarket.service.soap;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import com.geekbrains.ru.springmarket.repository.CategoryRepository;
import com.geekbrains.ru.springmarket.soap.soap.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public static final Function<CategoryEntity, Category> functionEntityToSoap = ge -> {
        Category g = new Category();
        g.setName(g.getName());
        ge.getProducts().stream().map(ProductService.functionEntityToSoap).forEach(s -> g.getProducts().add(s));
        return g;
    };

    public Category getByAlias(String alias) {
        return categoryRepository.findByAlias(alias).map(functionEntityToSoap).get();
    }
}
