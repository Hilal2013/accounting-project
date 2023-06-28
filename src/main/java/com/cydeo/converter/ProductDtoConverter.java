package com.cydeo.converter;

import com.cydeo.dto.ProductDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoConverter implements Converter<Long, ProductDTO> {
    @Override
    public ProductDTO convert(Long id) {
        return null;
    }
}
