package com.geekbrains.ru.springmarket.controller.mvc;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import com.geekbrains.ru.springmarket.service.CategoryService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static com.geekbrains.ru.springmarket.domain.constant.RequestNameConstant.CATEGORY;

@Controller
@AllArgsConstructor
@RequestMapping(CATEGORY)
public class CategoryController {

    private CategoryService categoryService;

    private final Validator validator;

    @GetMapping("/form")
    public String getCategoryForm(@RequestParam(required = false) Long id, Model model,
                                  @ModelAttribute(value = "violations") String violations) {
        if (id != null) {
            CategoryEntity category = categoryService.findById(id);
            model.addAttribute("category", category);
        } else {
            model.addAttribute("category", new CategoryEntity());
        }

        model.addAttribute("violations", violations);
        model.addAttribute("categories", categoryService.findAll());

        return "category/form";
    }

    @PostMapping
    public RedirectView saveCategory(CategoryEntity category, RedirectAttributes attributes) {
        Set<ConstraintViolation<CategoryEntity>> violationResult = validator.validate(category);
        if (CollectionUtils.isNotEmpty(violationResult)) {
            String violations = violationResult.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));

            attributes.addFlashAttribute("violations", violations);

            return new RedirectView("/category/form");
        }

        categoryService.save(category);

        return new RedirectView("/product/list");
    }


}
