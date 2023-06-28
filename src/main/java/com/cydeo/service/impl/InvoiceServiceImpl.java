package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    private final MapperUtil mapperUtil;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public InvoiceDTO findById(Long id) {

            return mapperUtil.convert(invoiceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Invoice couldn't find.")), new InvoiceDTO());
        }


    @Override
    public InvoiceDTO save(InvoiceDTO invoice) {

        return null;
    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoice) {

        return null;
    }



    @Override
    public List<InvoiceDTO> listAllInvoice() {
        return invoiceRepository.findAll().stream()
                .map(invoice-> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());

    }

    @Override
    public InvoiceDTO delete(Long id) {
        Invoice invoice = invoiceRepository.findByIdAndIsDeleted(id, false);
        InvoiceDTO invoiceDTO=mapperUtil.convert(invoice,new InvoiceDTO());
        if(invoiceDTO.getInvoiceStatus().getValue().equals("Awaiting Approval")) {
            invoice.setIsDeleted(true);
            invoiceRepository.save(invoice);
        }
        return invoiceDTO;
    }






}
