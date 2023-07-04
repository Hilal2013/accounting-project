package com.cydeo.converter;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.service.CategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationPropertiesBinding
public class CategoryDTOConverter implements Converter<String, CategoryDTO> {

    CategoryService categoryService;

    public CategoryDTOConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDTO convert(String source) {
        return categoryService.findById(Long.valueOf(source));
    }

}
