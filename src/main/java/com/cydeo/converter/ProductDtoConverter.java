package com.cydeo.converter;

import com.cydeo.dto.ProductDTO;
import com.cydeo.service.ProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoConverter implements Converter<Long, ProductDTO> {

    private final ProductService productService;

    public ProductDtoConverter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductDTO convert(Long id) {

        return productService.findById(id);

    }
}
