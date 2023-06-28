package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    //private final ProductService productService;
    //private final ClientVendorService clientVendorService;

    public PurchasesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
    }
    @GetMapping("/list")
    public String listAllPurchaseInvoices(Model model){
        model.addAttribute("invoices",invoiceService.listAllInvoice());
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String cratePurchaseInvoices(Model model){
        model.addAttribute("newPurchaseInvoice",new InvoiceDTO());
       // model.addAttribute("vendors",clientVendorService.listAllVendors());
        return "/invoice/purchase-invoice-create";
    }
    @PostMapping("/create")
    public String savePurchaseInvoice(@ModelAttribute("newPurchaseInvoice")InvoiceDTO invoiceDTO,Model model){

        return "/invoice/purchase-invoice-update";
    }

    @GetMapping("/update/{id}")
    public String editPurchaseInvoices(@PathVariable("id") Long id, Model model){
        model.addAttribute("invoice",invoiceService.findById(id));
        //clientVendorService.listAllVendors();

        //productService.listAllProduct();

        return "/invoice/purchase-invoice-update";

   }
    @GetMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id")Long id, Model model){
        model.addAttribute("newInvoiceProduct",new InvoiceProductDTO());
    return "redirect:/invoice/purchase-invoice-update";

    }
    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id")Long id,@ModelAttribute("newInvoiceProduct")InvoiceProductDTO invoiceProduct, Model model){

        return "redirect:/invoice/purchase-invoice-update";

    }


    @PostMapping("/update/{id}")
    public String updatePurchaseInvoices(@PathVariable("id") Long id,@ModelAttribute("invoice")InvoiceDTO invoice, Model model){
       invoiceService.update(invoice);
        return "/invoice/purchase-invoice-update";

    }


}
