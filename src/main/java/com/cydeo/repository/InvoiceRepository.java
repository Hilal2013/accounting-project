package com.cydeo.repository;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {


    Invoice findByIdAndIsDeleted(Long id,Boolean deleted);
    List<Invoice> findAllByInvoiceType(InvoiceType invoiceType);


}
