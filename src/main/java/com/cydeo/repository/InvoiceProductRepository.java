package com.cydeo.repository;


import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {

    InvoiceProduct findByInvoice_Id(Long id);
    List<InvoiceProduct> findAllByInvoiceId(Long id);

    InvoiceProduct findAllByInvoiceIdAndIsDeleted(Long id,Boolean deleted);


}
