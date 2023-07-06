package com.cydeo.repository;


import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {

    InvoiceProduct findByInvoice_Id(Long id);
    List<InvoiceProduct> findAllByInvoiceId(Long id);
    List<InvoiceProduct> findAllByIdAndIsDeleted(Long invoiceProductId,Boolean isDeleted);

    @Query(value = "SELECT SUM(quantity) FROM invoice_products WHERE product_id = ?1", nativeQuery = true)
    Integer countProductPurchased(@Param("productId") Long id);

    List<InvoiceProduct> findAllByInvoice_IdAndProduct_Id(Long invoiceId, Long productId);



}
