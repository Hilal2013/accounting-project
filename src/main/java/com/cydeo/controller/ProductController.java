package com.cydeo.controller;

import com.cydeo.dto.ProductDTO;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("products", productService.listAllProducts());

        return "/product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct(@ModelAttribute("product") ProductDTO product, Model model){

        productService.save(product);

        return "/product/product-create";
    }

    @GetMapping("/list")
    public String listAllProducts(Model model){

        model.addAttribute("products", productService.listAllProducts());

        return "/product/product-list";
    }

    @GetMapping("/update/{productId}")
    public String editUser(@PathVariable("productId") Long id, Model model) {

        model.addAttribute("product", productService.findById(id));

        return "/product/product-update";

    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){

        productService.delete(id);

        return "redirect:/product/product-list";
    }

}
