package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.DashboardService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final InvoiceService invoiceService;

    public DashboardServiceImpl(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public BigDecimal sumOfTotalCost() {

          return  invoiceService.listAllInvoice(InvoiceType.PURCHASE).stream()
                  .filter(invoiceDTO -> invoiceDTO.getInvoiceStatus()==InvoiceStatus.APPROVED)
                  .map(InvoiceDTO::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);

        }
    @Override
    public BigDecimal sumOfTotalSales() {
        return invoiceService.listAllInvoice(InvoiceType.SALES).stream()
                .filter(invoiceDTO -> invoiceDTO.getInvoiceStatus()==InvoiceStatus.APPROVED)
                .map(InvoiceDTO::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfTotalProfit_Loss() {
        return BigDecimal.valueOf(100.00);
    }
}
