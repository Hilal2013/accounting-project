package com.cydeo.controller;


import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Category;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String displayAllCategory(Model model){
        model.addAttribute("categories", categoryService.listAllCategories());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("newCategory", new Category());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("newCategory")CategoryDTO categoryDTO, Model model){
        categoryService.save(categoryDTO);
        return "redirect:/categories/list";
    }


    @GetMapping("/update/{id}")
    public String showPageEditCategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category",categoryService.findById(id));
        return "category/category-update";
    }

    @PostMapping("/update{id}")
    public String updateCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "category/category-update";
        }
        categoryService.updateCategory(id,categoryDTO);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id){

        categoryService.delete(id);

        return "redirect:/categories/list";
    }



}
