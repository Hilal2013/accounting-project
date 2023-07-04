package com.cydeo.service;

import com.cydeo.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public List<CategoryDTO> listAllCategories();

    CategoryDTO findById(long parseLong);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    void delete(Long categoryId);

    List<CategoryDTO> listAllCategoriesByCompany();

}
