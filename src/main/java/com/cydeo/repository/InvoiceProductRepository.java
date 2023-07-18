package com.cydeo.repository;


import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {

    InvoiceProduct findByInvoice_Id(Long id);

    List<InvoiceProduct> findAllByInvoiceId(Long id);

    List<InvoiceProduct> findAllByIdAndIsDeleted(Long invoiceProductId, Boolean isDeleted);

    @Query(value = "SELECT SUM(quantity) FROM invoice_products WHERE product_id = ?1", nativeQuery = true)
    Integer countProductPurchased(@Param("productId") Long id);

    List<InvoiceProduct> findAllByInvoice_IdAndProduct_Id(Long invoiceId, Long productId);

    List<InvoiceProduct> findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompanyTitle(InvoiceStatus invoiceStatus
            , InvoiceType invoiceType, String companyTitle);

    @Query(" Select ip from InvoiceProduct ip join Invoice i on ip.in" +
            "voice.id=i.id where i.invoiceStatus=?1 " +
            "And i.invoiceType=?2 And i.company.title=?3 And ip.product.id=?4 AND ip.remainingQuantity>0 ")
    List<InvoiceProduct> findAllByInvoiceProductsCompanyProductQuantityGreaterThanZero
            (@Param("invoiceStatus") InvoiceStatus invoiceStatus, @Param("invoiceType") InvoiceType invoiceType, String title, Long productId);

    List<InvoiceProduct> findAllByProductIdAndInvoiceCompanyTitleAndInvoiceInvoiceStatusAndInvoiceInvoiceType
            (Long productId, String companyTitle, InvoiceStatus invoiceStatus, InvoiceType invoiceType);

    List<InvoiceProduct> findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompany(InvoiceStatus invoiceStatus
            , InvoiceType invoiceType, Company company);


    List<InvoiceProduct> findAllByInvoiceInvoiceStatusAndInvoiceCompanyOrderByInvoiceInsertDateTimeDesc(InvoiceStatus invoiceStatus,Company company);
}
