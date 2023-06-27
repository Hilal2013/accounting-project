package com.cydeo.entity;

import com.cydeo.enums.ProductUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {

//    String name
//    int quantityInStock
//    int lowLimitAlert
//    ProductUnit productUnit / enum
//    Category category / many-to-one / will be seen under "category" column on the "products" table

    @Column
    private String name;
    @Column
    private int quantityInStock;
    @Column
    private int lowLimitAlert;
    @Enumerated
    private ProductUnit productUnit;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY) // Category doesn't exist yet.
    private Category category;
    public Product(String name, int quantityInStock, int lowLimitAlert, ProductUnit productUnit) {
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.lowLimitAlert = lowLimitAlert;
        this.productUnit = productUnit;
    }


}
