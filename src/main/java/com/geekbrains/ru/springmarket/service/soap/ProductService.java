package com.geekbrains.ru.springmarket.service.soap;

import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.repository.ProductRepository;
import com.geekbrains.ru.springmarket.soap.soap.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public static final Function<ProductEntity, Product> functionEntityToSoap = se -> {
        Product p = new Product();
        p.setId(se.getId());
        p.setTitle(se.getTitle());
        p.setPrice(se.getPrice());
        return p;
    };

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream()
                .map(functionEntityToSoap)
                .collect(Collectors.toList());
    }

    public Product getByTitle(String title) {
        return productRepository.findByTitle(title)
                .map(functionEntityToSoap)
                .get();
    }
}
