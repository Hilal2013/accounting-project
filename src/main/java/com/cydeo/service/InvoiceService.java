package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO save(InvoiceDTO invoice);
    InvoiceDTO update(InvoiceDTO invoice);
    void delete(String invoiceNo);

    List<InvoiceDTO> listAllInvoices();


}
