package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceService invoiceService;
    private final MapperUtil mapperUtil;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, InvoiceService invoiceService, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.invoiceService = invoiceService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<InvoiceProductDTO> listAllInvoiceProduct(Long id) {
        return invoiceProductRepository.findAllByInvoiceId(id).stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDTO save(InvoiceProductDTO invoiceProductDTO, Long id) {
        InvoiceDTO invoice = invoiceService.findById(id);
        invoiceProductDTO.setInvoice(invoice);
        InvoiceProduct invoiceProduct = invoiceProductRepository
                .save(mapperUtil.convert(invoiceProductDTO, new InvoiceProduct()));
        return mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());
    }

    @Override
    public InvoiceProductDTO findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDTO());
    }

    @Override
    public InvoiceProductDTO findByInvoiceId(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findByInvoice_Id(id), new InvoiceProductDTO());
    }

    @Override
    public InvoiceProductDTO delete(Long invoiceId,Long invoiceProductId) {
        InvoiceProduct invoiceProduct=invoiceProductRepository.findByInvoice_Id(invoiceId);
        invoiceProduct.setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct);
        InvoiceProductDTO invoiceProductDTO=mapperUtil.convert(invoiceProduct,new InvoiceProductDTO());
        return invoiceProductDTO;
    }


}
