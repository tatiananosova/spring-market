package com.geekbrains.ru.springmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@ToString(exclude = {"products", "subCategories"})
@EqualsAndHashCode(exclude = {"id", "products", "subCategories"})
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя категории обязательно")
    private String name;

    @NotBlank(message = "Алиас категории обязателен")
    private String alias;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parentCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "parentCategory")
    private Set<CategoryEntity> subCategories;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private Set<ProductEntity> products = new HashSet<>();

}
