package com.cydeo.dto;

import com.cydeo.enums.ProductUnit;
import lombok.*;

@Data
@NoArgsConstructor
public class ProductDTO {

//          - Long id
//          - String name
//          - Integer quantityInStock
//          - Integer lowLimitAlert
//          - ProductUnit productUnit
//          - CategoryDto category
    private Long id;
    private String name;
    private int quantityInStock;
    private int lowLimitAlert;
    private ProductUnit productUnit;
//    private CategoryDTO category;

    public ProductDTO(Long id, String name, int quantityInStock, int lowLimitAlert, ProductUnit productUnit) {
        this.id = id;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.lowLimitAlert = lowLimitAlert;
        this.productUnit = productUnit;
    }
}
