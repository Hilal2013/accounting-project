package com.cydeo.controller;

import com.cydeo.dto.ProductDTO;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String createProduct(Model model){

        model.addAttribute("newProduct", new ProductDTO());

        return "/product/product-create";
    }

    @PostMapping
    public String createProduct(@ModelAttribute("product") ProductDTO product, Model model){

        model.addAttribute("newProduct", new ProductDTO());
        productService.save(product);

        return "redirect: /product/product-create";
    }

    @GetMapping("/list")
    public String listAllProducts(Model model){
        model.addAttribute("products", productService.listAllProducts());
        return "/product/product-list";
    }


}
