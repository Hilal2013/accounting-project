package com.cydeo.converter;

import com.cydeo.dto.ProductDTO;
import com.cydeo.service.ProductService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ProductDtoConverter implements Converter<String, ProductDTO> {

    private final ProductService productService;

    public ProductDtoConverter(@Lazy ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductDTO convert(String id) {

        if (id == null || id.equals("")) {
            return null;
        }

        return productService.findById(Long.valueOf(id));

    }
}
