package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;

public interface InvoiceProductService {
    InvoiceProductDTO findById(Long id);
}
