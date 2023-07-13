package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    Invoice findByIdAndIsDeleted(Long id, Boolean deleted);

    List<Invoice> findAllByInvoiceType(InvoiceType invoiceType);

    List<Invoice> findAllByIsDeletedIsNot(Boolean isDeleted);

    List<Invoice> findAllByInvoiceTypeOrderByInvoiceNoDesc(InvoiceType invoiceType);

    List<Invoice> findAllByInvoiceTypeAndCompanyAndIsDeletedOrderByInvoiceNoDesc(InvoiceType invoiceType, Company company, Boolean isDeleted);

    List<Invoice> findAllByInvoiceTypeAndCompanyOrderByInvoiceNoDesc(InvoiceType invoiceType, Company company);

    @Query("SELECT i FROM Invoice i WHERE i.invoiceType = ?1 AND i.company = ?2 ORDER BY i.invoiceNo DESC ")
    List<Invoice> retrieveInvoiceByTypeAndCompany(InvoiceType invoiceType, Company company);

    List<Invoice> findTop3DistinctByCompanyAndInvoiceStatusAndIsDeletedOrderByDateDesc(Company company, InvoiceStatus invoiceStatus, Boolean isDeleted);

    boolean existsByCompanyAndAndClientVendorId(Company company, Long id);
}

