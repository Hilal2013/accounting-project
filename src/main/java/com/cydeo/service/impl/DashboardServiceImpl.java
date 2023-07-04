package com.cydeo.service.impl;

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
        return null;
    }

    @Override
    public BigDecimal sumOfTotalSales() {
        return null;
    }

    @Override
    public BigDecimal sumOfTotalProfit_Loss() {
        return null;
    }
}
