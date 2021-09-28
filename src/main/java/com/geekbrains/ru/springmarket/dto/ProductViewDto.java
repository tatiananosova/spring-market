package com.geekbrains.ru.springmarket.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewDto {

    private Long id;

    private String title;

    private Double price;

    private String imageLink;

}
