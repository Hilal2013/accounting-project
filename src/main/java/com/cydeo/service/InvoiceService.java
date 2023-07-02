package com.cydeo.service;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO findById(Long id);
    InvoiceDTO save(InvoiceDTO invoiceDTO, InvoiceType type);
    InvoiceDTO update(InvoiceDTO invoice);
    List<InvoiceDTO> listAllInvoice(InvoiceType invoiceType);

    InvoiceDTO delete(Long id);

    InvoiceDTO approve(Long id);

    InvoiceDTO createNewSalesInvoice();

    InvoiceDTO createNewPurchasesInvoice();
    String findInvoiceId();


}
