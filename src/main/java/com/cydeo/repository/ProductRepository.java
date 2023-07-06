package com.cydeo.repository;

import com.cydeo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();
    Product findByName(String name);

//    List<Product> findAllByIsDeletedOrderByCategoryAscNameAsc(boolean logic);

    List<Product> findAllByCategory_Company_IdOrderByCategoryAscNameAsc(Long id);


    List<Product> findAllByCategory_Company_IdOrderByCategoryAscNameAsc(Long id);
    Product findByName(String name);
}
