package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {
    private final InvoiceService invoiceService;

    public PurchasesInvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping("/create")
    public String cratePurchaseInvoices(Model model){
        model.addAttribute("purchase",new InvoiceDTO());
        return "/invoice/purchase-invoice-create";
    }
    @GetMapping("/list")
    public String listAllPurchaseInvoices(@ModelAttribute("purchase")InvoiceDTO invoice,Model model){
        model.addAttribute("purchases",invoiceService.listAllInvoice());
        return "/invoice/purchase-invoice-list";
    }

    @PostMapping("/update/{id}")
    public String updatePurchaseInvoices(@PathVariable("id") Long id){
        return "/invoice/purchase-invoice-update";

   }

}
