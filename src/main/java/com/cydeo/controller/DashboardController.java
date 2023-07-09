package com.cydeo.controller;

import com.cydeo.client.CurrencyClient;
import com.cydeo.dto.ExchangeRates;
import com.cydeo.dto.currency.Usd;
import com.cydeo.service.DashboardService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final InvoiceService invoiceService;


    public DashboardController(DashboardService dashboardService, InvoiceService invoiceService, CurrencyClient currencyClient) {
        this.dashboardService = dashboardService;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String getDashboard(Model model) {

        Map<String, BigDecimal> summaryNumbers = new HashMap<>();
        summaryNumbers.put("totalCost", dashboardService.sumOfTotalCost());
        summaryNumbers.put("totalSales",dashboardService.sumOfTotalSales());
        summaryNumbers.put("profitLoss",dashboardService.sumOfTotalProfit_Loss());
        model.addAttribute("summaryNumbers",summaryNumbers);
        model.addAttribute("invoices",invoiceService.listAllInvoiceForDashBoard());

        model.addAttribute("exchangeRates",dashboardService.getRates());

        return "/dashboard";
    }
}



