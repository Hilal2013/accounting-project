package com.cydeo.entity;

import com.cydeo.enums.ProductUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
@Where(clause = "is_deleted = false")
public class Product extends BaseEntity {

    @Column
    private String name;
    @Column
    private int quantityInStock;
    @Column
    private int lowLimitAlert;
    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;
    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public Product(String name, int quantityInStock, int lowLimitAlert, ProductUnit productUnit) {
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.lowLimitAlert = lowLimitAlert;
        this.productUnit = productUnit;
    }


}
