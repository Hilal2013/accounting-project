package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO createInvoice();
    InvoiceDTO updateInvoice();
    List<InvoiceDTO> listAllInvoice();

    void deleteInvoice(String invoiceNo);

}

