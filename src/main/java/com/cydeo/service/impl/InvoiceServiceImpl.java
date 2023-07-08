package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.exception.InvoiceNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CompanyService companyService;
    private final InvoiceProductService invoiceProductService;
    private final InvoiceProductRepository invoiceProductRepository;
    private final ProductService productService;


    private final MapperUtil mapperUtil;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CompanyService companyService, @Lazy InvoiceProductService invoiceProductService, InvoiceProductRepository invoiceProductRepository, ProductService productService, MapperUtil mapperUtil) {
        this.invoiceRepository = invoiceRepository;
        this.companyService = companyService;
        this.invoiceProductService = invoiceProductService;
        this.invoiceProductRepository = invoiceProductRepository;
        this.productService = productService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public InvoiceDTO findById(Long id) {
        InvoiceDTO invoiceDTO = mapperUtil.convert(invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice couldn't find.")), new InvoiceDTO());
        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.listAllInvoiceProduct(id);
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (InvoiceProductDTO invoiceProductDTO : invoiceProductDTOList) {
            BigDecimal price = invoiceProductDTO.getPrice()
                    .multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()));
            subtotal = subtotal.add(price);
            tax = tax.add(price.multiply(BigDecimal.valueOf((invoiceProductDTO.getTax() * 0.01))));
        }
        grandTotal = subtotal.add(tax);
        invoiceDTO.setPrice(subtotal);
        invoiceDTO.setTax(tax);
        invoiceDTO.setTotal(grandTotal);
        return invoiceDTO;
    }


    @Override
    public InvoiceDTO save(InvoiceDTO invoice, InvoiceType type) {
        invoice.setCompany(companyService.getCompanyDTOByLoggedInUser());
        Invoice invoice1 = mapperUtil.convert(invoice, new Invoice());
        invoice1.setInvoiceType(type);
        invoice1.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoice1.setDate(LocalDate.now());
        invoiceRepository.save(invoice1);
        return mapperUtil.convert(invoice1, new InvoiceDTO());


    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoice) {

        Optional<Invoice> invoice2 = invoiceRepository.findById(invoice.getId());

        Invoice updatedInvoice = mapperUtil.convert(invoice, new Invoice());
        updatedInvoice.setClientVendor(invoice2.orElseThrow(()->new InvoiceNotFoundException("Invoice can not found!.")).getClientVendor());
        invoiceRepository.save(updatedInvoice);
        return mapperUtil.convert(updatedInvoice, new InvoiceDTO());
    }


    @Override
    public List<InvoiceDTO> listAllInvoice(InvoiceType invoiceType) {
        CompanyDTO companyDTO=companyService.getCompanyDTOByLoggedInUser();
        Company company=mapperUtil.convert(companyDTO,new Company());

        return invoiceRepository.findAllByInvoiceTypeAndCompanyAndIsDeletedOrderByInvoiceNoDesc(invoiceType,company,false).stream()
                .map(invoice -> calculateTotal(invoice.getId()))
                .map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());

    }

    @Override
    public InvoiceDTO delete(Long invoiceId) {
        Invoice invoice = invoiceRepository.findByIdAndIsDeleted(invoiceId, false);
        if (invoice.getInvoiceStatus().equals(InvoiceStatus.AWAITING_APPROVAL)) {
            invoice.setIsDeleted(true);
        }
        invoiceProductRepository.findAllByInvoiceId(invoice.getId()).stream()
                    .map(invoiceProduct -> invoiceProductService.delete(invoiceProduct.getId())).collect(Collectors.toList());
        invoiceRepository.save(invoice);
        return mapperUtil.convert(invoice,new InvoiceDTO());
        }


    @Override
    public InvoiceDTO approve(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        if (invoice.getInvoiceType().getValue().equals(InvoiceType.PURCHASE.getValue())) {
            List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.listAllInvoiceProduct(id);
            for (InvoiceProductDTO invoiceProductDTO : invoiceProductDTOList) {
                Long productId = invoiceProductDTO.getProduct().getId();
                Integer amount = invoiceProductDTO.getQuantity();
                productService.increaseProductInventory(productId, amount);
            }
        } else if (invoice.getInvoiceType().getValue().equals(InvoiceType.SALES.getValue())) {
            List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.listAllInvoiceProduct(id);
            for (InvoiceProductDTO invoiceProductDTO : invoiceProductDTOList) {
                Long productId = invoiceProductDTO.getProduct().getId();
                Integer amount = invoiceProductDTO.getQuantity();
                productService.decreaseProductInventory(productId, amount);
            }
        }
        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoiceRepository.save(invoice);
        return mapperUtil.convert(invoice, new InvoiceDTO());
    }

    @Override
    public InvoiceDTO createNewSalesInvoice() {
        CompanyDTO companyDTO = companyService.getCompanyDTOByLoggedInUser();
        Company company = mapperUtil.convert(companyDTO,new Company());
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        int no = invoiceRepository.retrieveInvoiceByTypeAndCompany(InvoiceType.SALES, company).size() + 1;
        invoiceDTO.setInvoiceNo(no < 10 ? "S-00" + no : no < 100 ? "S-0" + no : "S-" + no);
        invoiceDTO.setDate(LocalDate.now());
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        return invoiceDTO;
    }

    @Override
    public InvoiceDTO createNewPurchasesInvoice() {
        CompanyDTO companyDTO=companyService.getCompanyDTOByLoggedInUser();
        Company company=mapperUtil.convert(companyDTO,new Company());
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        int no = invoiceRepository.findAllByInvoiceTypeAndCompanyOrderByInvoiceNoDesc(InvoiceType.PURCHASE,company).size() + 1;
        if(no<10) invoiceDTO.setInvoiceNo("P-00" + no);
        else if(no<100 && no>=10)  invoiceDTO.setInvoiceNo("P-0" + no);
        else  invoiceDTO.setInvoiceNo("P-" + no);
        invoiceDTO.setDate(LocalDate.now());
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        return invoiceDTO;


    }

    @Override
    public String findInvoiceId() {

        return String.valueOf(invoiceRepository.findAll().size());
    }


    private InvoiceDTO calculateTotal(Long id) {
        InvoiceDTO invoiceDTO=findById(id);
        List<InvoiceProductDTO> productList = invoiceProductService.listAllInvoiceProduct(id);
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        BigDecimal totalWithTax=BigDecimal.valueOf(0);
        BigDecimal tax=BigDecimal.valueOf(0);
        for (InvoiceProductDTO each : productList) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice()));
            tax=tax.add(BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice()).multiply(BigDecimal.valueOf(each.getTax()).divide(BigDecimal.valueOf(100))).setScale(2));
            totalWithTax = totalPrice.add(tax);

        }
        invoiceDTO.setPrice(totalPrice);
        invoiceDTO.setTax(tax);
        invoiceDTO.setTotal(totalWithTax.setScale(2));
        return invoiceDTO;

    }
    @Override
    public List<InvoiceDTO> listAllInvoiceForDashBoard() {
        CompanyDTO companyDTO=companyService.getCompanyDTOByLoggedInUser();
        Company company=mapperUtil.convert(companyDTO,new Company());

        return invoiceRepository.findTop3DistinctByCompanyAndInvoiceStatusAndIsDeletedOrderByDateDesc(company,InvoiceStatus.APPROVED,false).stream()
                .map(invoice -> calculateTotal(invoice.getId()))
                .map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());

    }



}









