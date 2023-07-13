package com.cydeo.service.impl;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.ReportingService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;
    private final MapperUtil mapperUtil;


    public ReportingServiceImpl(InvoiceProductRepository invoiceProductRepository, InvoiceProductService invoiceProductService, CompanyService companyService, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public Map<String, BigDecimal> getProfitLossByMonth() {
        CompanyDTO companyDTO=companyService.getCompanyDTOByLoggedInUser();
        Company company=mapperUtil.convert(companyDTO,new Company());
        Map<String,BigDecimal> mapMonthlyProfitLoss=new HashMap<>();
        List<InvoiceProduct> list=invoiceProductRepository.findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompany(InvoiceStatus.APPROVED,InvoiceType.SALES,company);
        for (InvoiceProduct invoiceProduct : list) {
            String yearOfMonth = invoiceProduct.getInvoice().getDate().getYear() + " " + invoiceProduct.getInvoice().getDate().getMonth();
            BigDecimal profitLoss = invoiceProduct.getProfitLoss();
            mapMonthlyProfitLoss.put(yearOfMonth, profitLoss);
        }
        return mapMonthlyProfitLoss;


    }

    @Override
    public List<InvoiceProductDTO> getAllStockReport() {
        CompanyDTO companyDTO=companyService.getCompanyDTOByLoggedInUser();
        Company company=mapperUtil.convert(companyDTO,new Company());
        List<InvoiceProduct> list=invoiceProductRepository.findAllByInvoiceInvoiceStatusAndInvoiceCompanyOrderByInvoiceInsertDateTimeDesc(InvoiceStatus.APPROVED,company);
        return list.stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct,new InvoiceProductDTO())).collect(Collectors.toList());
    }
}

