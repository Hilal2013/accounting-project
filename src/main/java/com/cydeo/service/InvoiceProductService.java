package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;

import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDTO> listAllInvoiceProduct();
    InvoiceProductDTO findById(Long id);
    InvoiceProductDTO findByInvoiceId(Long id);
}
