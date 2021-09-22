package com.geekbrains.ru.springmarket.dto.mapping;

import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.dto.ProductViewDto;
import org.modelmapper.ModelMapper;

public class ProductViewMapper {
    public static ProductViewDto convertEntityToDto(ProductEntity savedEntity) {
        return new ModelMapper().map(savedEntity, ProductViewDto.class);
    }

    public static ProductEntity convertDtoToEntity(ProductViewDto savedDto) {
        return new ModelMapper().map(savedDto, ProductEntity.class);
    }
}
