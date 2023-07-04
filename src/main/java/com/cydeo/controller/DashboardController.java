package com.cydeo.controller;

import com.cydeo.entity.Currency;
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

    public DashboardController(DashboardService dashboardService, InvoiceService invoiceService) {
        this.dashboardService = dashboardService;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String getDashboard(Model model) {
        Map<String, BigDecimal> mapTotal = new HashMap<>();
        mapTotal.put("totalCost",BigDecimal.valueOf(2500.00));
        mapTotal.put("totalSales", BigDecimal.valueOf(600.00));
        mapTotal.put("profitLoss",BigDecimal.valueOf(1900.00));
        model.addAttribute("summaryNumbers", mapTotal);
//model.addAttribute("invoices",invoiceService.lastTransactionsForDashboard());

        model.addAttribute("exchangeRates",new Currency());



        return "/dashboard";

    }


}

//model.addAttribute("summaryNumbers",dashboardService.sumOfTotalCost());
//model.addAttribute("summaryNumbers",dashboardService.sumOfTotalSales());
//model.addAttribute("summaryNumbers",dashboardService.sumOfTotalProfit_Loss());

// model.addAttribute("curre")
//
//        Map<String,String> mapExchange = new HashMap<>();
//       mapExchange.put("euro", "1.09");
////        mapExchange.put("britishPound", new BigDecimal(1.27));
////        mapExchange.put("canadianDollar", new BigDecimal(0.75));
////        mapExchange.put("japaneseYen", new BigDecimal(0.0069));
////        mapExchange.put("indianRupee", new BigDecimal(0.012));
//        model.addAttribute("exchangeRates",mapExchange);

//     model.addAttribute("invoices",invoiceRepository.findAll());


