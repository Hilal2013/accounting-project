package com.cydeo.converter;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.service.CategoryService;
import com.cydeo.service.CompanyService;
import org.springframework.core.convert.converter.Converter;

public class CategoryConverter implements Converter<Long, CategoryDTO> {

    CategoryService categoryService;

    public CategoryConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @Override
    public CategoryDTO convert(Long source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return categoryService.findById(Long.parseLong(String.valueOf(source)));
    }
}
