package com.cydeo.controller;

import com.cydeo.dto.ProductDTO;
import com.cydeo.enums.ProductUnit;
import com.cydeo.service.CategoryService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;


    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/create")
    public String createProduct(Model model){

        model.addAttribute("newProduct", new ProductDTO());
        model.addAttribute("categories", categoryService.listAllCategories());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));

        return "/product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct(@ModelAttribute("product") ProductDTO product, Model model, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/product/product-create";
        }
        productService.save(product);

        return "redirect:/products/create";
    }

    @GetMapping("/list")
    public String listAllProducts(Model model){

        model.addAttribute("products", productService.listAllProducts());

        return "/product/product-list";
    }

    @GetMapping("/update/{productId}")
    public String editProduct(@PathVariable("productId") Long id, Model model) {

        model.addAttribute("categories", categoryService.listAllCategories());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
        model.addAttribute("product", productService.findById(id));

        return "/product/product-update";

    }

    @PostMapping("/update/")
    public String updateProduct(@Valid @ModelAttribute("product") ProductDTO product, Model model, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/product/product-list";
        }
        productService.update(product);

        return "redirect:/product/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){

        productService.delete(id);

        return "redirect:/products/list";
    }

}
