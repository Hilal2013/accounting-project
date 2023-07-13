package com.cydeo.controller;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("reports")
public class ReportingController {
    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/profitLossData")
    public String getProfitLossReport(Model model){
        model.addAttribute("monthlyProfitLossDataMap",reportingService.getProfitLossByMonth());
        return "/report/profit-loss-report";

    }
    @GetMapping("/stockData")
   public String getStockReport(Model model){
   //model.addAttribute("invoiceProducts",reportingService.getAllStockReport());
        model.addAttribute("invoiceProducts",reportingService.getAllStockReport());
       return "/report/stock-report";
   }


}
