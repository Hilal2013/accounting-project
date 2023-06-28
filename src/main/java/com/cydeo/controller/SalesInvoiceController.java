package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
//    private final ProductService productService;
//    private final ClientVendorService clientVendorService;

    public SalesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
//        this.productService = invoiceProductService;
//        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listAllSalesInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.listAllInvoice());
        return "invoice/sales-invoice-list";
    }

//    @GetMapping("/update/{id}")
//    public String editSalesInvoice(@PathVariable Long id, Model model) {
//        model.addAttribute("invoice", invoiceService.findById(id));
//        model.addAttribute("clients", clientVendorService.listAllClientVendor());
//        model.addAttribute("newInvoiceProduct", invoiceProductService.findByInvoiceId(id));
//        model.addAttribute("products", productService.listAllProducts());
//        return "invoice/sales-invoice-update";
//    }

    @PostMapping("/update")
    public String updateSalesInvoice(@ModelAttribute("invoice")InvoiceDTO invoice) {
        invoiceService.update(invoice);
        return "redirect:invoice/sales-invoice-list";
    }





}
