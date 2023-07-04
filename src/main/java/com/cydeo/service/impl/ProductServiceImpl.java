package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.ProductDTO;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.mapper.ProductMapper;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.CompanyService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final ProductMapper productMapper;
    private final CompanyService companyService;


    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, ProductMapper productMapper,
                              CategoryRepository categoryRepository, CategoryService categoryService, CompanyService companyService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.productMapper = productMapper;
        this.companyService = companyService;
    }

    @Override
    public List<ProductDTO> listAllProducts() {

        CompanyDTO company = companyService.getCompanyDTOByLoggedInUser();
        List<Product> productsList = productRepository.findAllByCategory_Company_IdOrderByCategoryAscNameAsc(company.getId());

        return productsList.stream().map(

                product ->
                        mapperUtil.convert(product, new ProductDTO())).collect(Collectors.toList()
        );
    }

    @Override
    public void save(ProductDTO product) {

        Product convertedProduct = mapperUtil.convert(product, new Product());

        productRepository.save(convertedProduct);
    }

    @Override
    public ProductDTO findById(Long id) {


        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return productMapper.convertToDto(product.get());
        }

        return null;
    }

    @Override
    public void delete(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            product.get().setIsDeleted(true);
            productRepository.save(product.get());
        }

    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Product convertedProduct = mapperUtil.convert(dto, new Product());
        convertedProduct.setId(product.getId());
        productRepository.save(convertedProduct);
        return mapperUtil.convert(convertedProduct,new ProductDTO());
    }

//    /**
//     *
//     * @param company
//     * @return
//     */
//    @Override
//    public List<ProductDTO> findProcuctsByCompany(Company company) {
//        List<Product> product = productRepository.findAllByCompany(company);
//        return product.stream().map(product1 -> mapperUtil.convert(product, new ProductDTO())).collect(Collectors.toList());
//    }


}
