package com.geekbrains.ru.springmarket.controller.mvc;

import com.geekbrains.ru.springmarket.component.ShoppingCart;
import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/cart")
@SessionAttributes("shoppingCart")
@AllArgsConstructor
public class CartController {

    private final ProductService productService;

    @GetMapping("/list")
    public String showCart() {
        return "cart/list";
    }

    @GetMapping("/add-to-cart")
    public RedirectView addToCart(@RequestParam Long id, @ModelAttribute ShoppingCart cart) {
        ProductEntity product = productService.findById(id);
        cart.addProduct(product);

        return new RedirectView("/product/list");
    }

    @GetMapping("/remove-from-cart")
    public RedirectView removeFromCart(@RequestParam Long id, @ModelAttribute ShoppingCart cart) {
        ProductEntity product = productService.findById(id);
        cart.removeProduct(product);

        return new RedirectView("/cart/list");
    }

}
