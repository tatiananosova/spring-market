package com.geekbrains.ru.springmarket.controller.mvc;

import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.service.CategoryService;
import com.geekbrains.ru.springmarket.service.ProductService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static com.geekbrains.ru.springmarket.domain.constant.RequestNameConstant.PRODUCT;

@Controller
@AllArgsConstructor
@RequestMapping(PRODUCT)
@SessionAttributes("shoppingCart")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    private final Validator validator;

    @GetMapping("/list")
    public String getProducts(@RequestParam(required = false) Integer pageNum,
                              @RequestParam(value = "category", required = false) String categoryAlias,
                              Model model) {

        final int pageSize = 5;

        Pageable pageRequest = PageRequest.of(pageNum == null ? 0 : pageNum, pageSize);
        Page<ProductEntity> page = productService.findAllByPageAndCategory(pageRequest, categoryAlias);

        model.addAttribute("page", page);
        model.addAttribute("categoryTree", categoryService.getCategoryTree());

        return "product/list";
    }

    @GetMapping("/form")
    public String getProductForm(@RequestParam(required = false) Long id, Model model,
                                 @ModelAttribute(value = "violations") String violations) {

        if (id != null) {
            ProductEntity product = productService.findById(id);
            model.addAttribute("product", product);
        } else {
            model.addAttribute("product", new ProductEntity());
        }

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("violations", violations);

        return "product/form";
    }

    @PostMapping
    public RedirectView saveProduct(ProductEntity product,
                                       @RequestParam(required = false) MultipartFile image,
                                       RedirectAttributes attributes) {

        Set<ConstraintViolation<ProductEntity>> violationResult = validator.validate(product);
        if (CollectionUtils.isNotEmpty(violationResult)) {
            String violations = violationResult.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));

            attributes.addFlashAttribute("violations", violations);

            return new RedirectView("/product/form");
        }

        productService.saveWithImage(product, image);

        return new RedirectView("/product/list");
    }

    @GetMapping("/delete")
    public RedirectView deleteProductById(@RequestParam Long id) {
        productService.deleteById(id);

        return new RedirectView("/product/list");
    }

}
