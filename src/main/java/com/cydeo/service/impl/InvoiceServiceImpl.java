package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void delete(Long id) {

    }

//    @Override
//    public InvoiceDTO findByInvoiceNo(String invoiceNo) {
//        return null;
//    }




}
