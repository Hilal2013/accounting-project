package com.cydeo.service;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.ProductDTO;

import java.util.List;

public interface CategoryService {

    public List<CategoryDTO> listAllCategories();

    CategoryDTO findById(long parseLong);
}
