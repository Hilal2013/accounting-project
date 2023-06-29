package com.cydeo.controller;


import com.cydeo.dto.CategoryDTO;
import com.cydeo.entity.Category;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String displayAllCategory(Model model){
        model.addAttribute("categories", categoryService.getCategoryList());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("newCategory", new Category());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("newCategory")CategoryDTO categoryDTO, Model model){
        return "redirect:/categories/list";
    }


}
