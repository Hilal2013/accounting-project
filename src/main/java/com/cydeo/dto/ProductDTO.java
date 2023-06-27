package com.cydeo.dto;

import jdk.jfr.Category;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
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
    private CategoryDTO category;

}
