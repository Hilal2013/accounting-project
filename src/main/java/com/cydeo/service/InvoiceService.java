package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO findById(Long id);
    InvoiceDTO save(InvoiceDTO invoice);
    InvoiceDTO update(InvoiceDTO invoice);
    List<InvoiceDTO> listAllInvoice();

    InvoiceDTO delete(Long id);

    InvoiceDTO approve(Long id);
}

