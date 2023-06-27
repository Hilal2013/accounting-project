package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public InvoiceDTO save(InvoiceDTO invoice) {
        return null;
    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoice) {
        return null;
    }

    @Override
    public void delete(String invoiceNo) {

    }

    @Override
    public List<InvoiceDTO> listAllInvoices() {
        return null;
    }
}
