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
    public String createCategory(@Valid @ModelAttribute("newCategory")CategoryDTO categoryDTO, BindingResult bindingResult){
        boolean categoryDescriptionNotUnique=categoryService.isCategoryDescriptionUnique(categoryDTO);
        if (categoryDescriptionNotUnique){
            bindingResult.rejectValue("description", " ", "This description is already exists.");
        }
        if (bindingResult.hasErrors()){
            return "/category/category-create";
        }
        categoryService.save(categoryDTO);
        return "redirect:/categories/list";
    }

    @GetMapping("/update/{categoryId}")
    public String showPageEditCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        model.addAttribute("category",categoryService.findById(categoryId));
        return "category/category-update";
    }

    @PostMapping("/update/{categoryId}")
    public String showPageEditCategory(@PathVariable("categoryId") Long categoryId,
                                       @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                       BindingResult bindingResult, Model model){
        boolean categoryDescriptionNotUnique = categoryService.isCategoryDescriptionUnique(categoryDTO);
        if (categoryDescriptionNotUnique) {
            bindingResult.rejectValue("description", " ", "This description is already exists.");
        }
        if (bindingResult.hasErrors()) {
            return "category/category-update";
        }
        categoryService.updateCategory(categoryId, categoryDTO);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId){

        categoryService.delete(categoryId);

        return "redirect:/categories/list";
    }

    }


/*

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

/// was working ,but not function
 */

/*
@GetMapping("/list") // 4 July 2023
    public String displayAllCategory(Model model){
        model.addAttribute("categories", categoryService.listAllCategories());
        return "/category/category-list";
    }

     @GetMapping("/update/{categoryId}") // 13 July 2023
    public String showPageEditCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        model.addAttribute("category",categoryService.findById(categoryId));
        return "category/category-update";
    }

    @PostMapping("/update/{categoryId}")
    public String updateCategory(@PathVariable("categoryId") Long categoryId,
                                 @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "category/category-update";
        }
        categoryService.updateCategory(categoryId,categoryDTO);
        return "redirect:/categories/list";
    }
    ///


    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId){

        categoryService.delete(categoryId);

        return "redirect:/categories/list";
    }

}


    @PostMapping("/update/{categoryId}")--> was working?????
    public String updateCategory(@PathVariable("categoryId") Long categoryId,
                                 @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "category/category-update";
        }
        categoryService.updateCategory(categoryId,categoryDTO);
        return "redirect:/categories/list";
    }

 */

    /*
    @PostMapping("/update/{id}")

    public String updateCategory(@PathVariable("id") Long id,
                                 @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                 BindingResult bindingResult, Model model) {

        boolean categoryDescriptionNotUnique=categoryService.isCategoryDescriptionUnique(categoryDTO);
        if (categoryDescriptionNotUnique){
            bindingResult.rejectValue("description", " ", "This description is already exists.");
        }
        if (bindingResult.hasErrors()) {
            return "category/category-update";
        }
        categoryService.updateCategory(id,categoryDTO);
        return "redirect:/categories/list";
    }

     */
