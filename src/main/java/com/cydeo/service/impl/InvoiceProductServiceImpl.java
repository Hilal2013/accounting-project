package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceService invoiceService;
    private final CompanyService companyService;
    private final MapperUtil mapperUtil;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, @Lazy InvoiceService invoiceService, CompanyService companyService, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.invoiceService = invoiceService;
        this.companyService = companyService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<InvoiceProductDTO> listAllInvoiceProduct(Long id) {
        return invoiceProductRepository.findAllByInvoiceId(id).stream()
                .map(invoiceProduct -> calculateTotalInvoiceProduct(id))
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());
    }
    private InvoiceProductDTO calculateTotalInvoiceProduct(Long id){
        InvoiceProductDTO invoiceProductDTO=findByInvoiceId(id);
        BigDecimal total=BigDecimal.ZERO;
        List<InvoiceProduct> list =invoiceProductRepository.findAllByInvoiceId(id);
        for (InvoiceProduct each : list) {
            total=total.add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())));//15
            total=total.add(total.multiply(BigDecimal.valueOf(each.getTax()).divide(BigDecimal.valueOf(100))));
        }
        invoiceProductDTO.setTotal(total);

        return invoiceProductDTO;
    }

    @Override
    public InvoiceProductDTO save(InvoiceProductDTO invoiceProductDTO, Long id) {
        CompanyDTO companyDTO=companyService.getCompanyDTOByLoggedInUser();
        InvoiceDTO invoice = invoiceService.findById(id);
        invoice.setCompany(companyDTO);
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
    public InvoiceProductDTO delete(Long invoiceProductId) {
        Optional<InvoiceProduct> invoiceProduct=invoiceProductRepository.findById(invoiceProductId);
        invoiceProduct.orElseThrow().setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct.orElseThrow());
        InvoiceProductDTO invoiceProductDTO=mapperUtil.convert(invoiceProduct,new InvoiceProductDTO());
        return invoiceProductDTO;
    }

}
