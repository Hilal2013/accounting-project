package com.cydeo.dto;

import com.cydeo.enums.ProductUnit;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    @NotNull(message = "name is a required field.")
    @Size(min = 2, max = 100)
    private String name;
    private int quantityInStock;

    @NotNull(message = "low limit alert is a required field")
    @Min(1)
    private int lowLimitAlert;
    @NotNull(message = "Product unit is a required field.")
    private ProductUnit productUnit;

    @NotNull(message = "Category is a required field.")
    private CategoryDTO category;

    public ProductDTO(Long id, String name, int quantityInStock, int lowLimitAlert, ProductUnit productUnit) {
        this.id = id;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.lowLimitAlert = lowLimitAlert;
        this.productUnit = productUnit;
    }
}
