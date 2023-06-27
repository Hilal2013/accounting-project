package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO save(InvoiceDTO invoice);
    InvoiceDTO update(InvoiceDTO invoice);
    List<InvoiceDTO> listAllInvoice();

    void delete(Long id);

}

