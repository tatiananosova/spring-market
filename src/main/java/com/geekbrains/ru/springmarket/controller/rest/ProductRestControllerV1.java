package com.geekbrains.ru.springmarket.controller.rest;

import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.dto.ProductViewDto;
import com.geekbrains.ru.springmarket.dto.mapping.ProductViewMapper;
import com.geekbrains.ru.springmarket.service.CategoryService;
import com.geekbrains.ru.springmarket.service.ProductService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.geekbrains.ru.springmarket.domain.constant.RequestNameConstant.API_V1;
import static com.geekbrains.ru.springmarket.domain.constant.RequestNameConstant.PRODUCT;

@RestController
@AllArgsConstructor
@RequestMapping(API_V1 + PRODUCT)
public class ProductRestControllerV1 {

    private final ProductService productService;
    private final CategoryService categoryService;

    private final Validator validator;

    @GetMapping()
    public List<ProductViewDto> getProducts(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(value = "category", required = false) String categoryAlias,
                                            Model model) {

        final int pageSize = 5;

        Pageable pageRequest = PageRequest.of(pageNum == null ? 0 : pageNum, pageSize);
        Page<ProductEntity> page = productService.findAllByPageAndCategory(pageRequest, categoryAlias);

        model.addAttribute("page", page);
        model.addAttribute("categoryTree", categoryService.getCategoryTree());

        return page.stream().map(ProductViewMapper::convertEntityToDto).collect(Collectors.toList());
    }

    @PostMapping()
    public ProductEntity saveProduct(@RequestBody ProductViewDto product,
                                     @RequestParam(required = false) MultipartFile image) {
        ProductEntity productEntity = ProductViewMapper.convertDtoToEntity(product);
        Set<ConstraintViolation<ProductEntity>> violationResult = validator.validate(productEntity);
        if (CollectionUtils.isNotEmpty(violationResult)) {
            throw new IllegalStateException();
        }

        return productService.saveWithImage(productEntity, image);
    }

    @DeleteMapping()
    public void deleteProductById(@RequestParam Long id) {
        productService.deleteById(id);
    }

}
