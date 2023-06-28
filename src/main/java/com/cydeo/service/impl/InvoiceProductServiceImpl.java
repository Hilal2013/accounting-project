package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<InvoiceProductDTO> listAllInvoiceProduct() {
        return invoiceProductRepository.findAll().stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDTO findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDTO());
    }

    @Override
    public InvoiceProductDTO findByInvoiceId(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findByInvoice_Id(id), new InvoiceProductDTO());
    }


}
