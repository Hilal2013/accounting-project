package com.cydeo.repository;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.enums.InvoiceType;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {


    Invoice findByIdAndIsDeleted(Long id,Boolean deleted);
    List<Invoice> findAllByInvoiceTypeOrderByInvoiceNoDesc(InvoiceType invoiceType);
    List<Invoice> findAllByInvoiceTypeAndCompanyOrderByInvoiceNoDesc(InvoiceType invoiceType,Company company);












}
