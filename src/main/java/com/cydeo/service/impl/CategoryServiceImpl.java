package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, CompanyService companyService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
    }

    @Override
    public List<CategoryDTO> listAllCategories() {

        List<Category> categoriesList = categoryRepository.findAll();
        return categoriesList.stream().map(

                category ->
                        mapperUtil.convert(category, new CategoryDTO())).collect(Collectors.toList()
        );
    }

    @Override
    public CategoryDTO findById(long parseLong) {

        Optional<Category> category = categoryRepository.findById(parseLong);

        return mapperUtil.convert(category, new CategoryDTO());
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = mapperUtil.convert(categoryDTO, new Category());
        categoryRepository.save(category);
        return mapperUtil.convert(category, new CategoryDTO());
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category categoryInDb = categoryRepository.findById(categoryId).orElseThrow(()->
        new NoSuchElementException("This category doesn't exist."));
        categoryInDb.setDescription(categoryDTO.getDescription());
        return mapperUtil.convert(categoryRepository.save(categoryInDb), new CategoryDTO());
    }

    @Override
    public void delete(Long categoryId) {
        Category categoryInDb = categoryRepository.findById(categoryId).orElseThrow(()->
                new NoSuchElementException("This category doesn't exist."));
        categoryInDb.setIsDeleted(true);
        categoryInDb.setDescription(categoryInDb.getDescription()+"-"+categoryInDb.getId());
        categoryRepository.save(categoryInDb);
    }


    @Override
    public List<CategoryDTO> listAllCategoriesByCompany(){
        List<Category> categoryList = categoryRepository
                .getAllProductsOrderByCompanyCompanyAsc(companyService.getCompanyDTOByLoggedInUser().getId());
        return categoryList.stream()
                .sorted(Comparator.comparing(Category::getDescription))
                .map(category -> mapperUtil.convert(category, new CategoryDTO()))
                .collect(Collectors.toList());
    }


}
