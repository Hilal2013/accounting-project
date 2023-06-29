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
        //model.addAttribute("products", productService.listAllProducts());
        return "/invoice/purchase-invoice-create";
    }
    @PostMapping("/create")
    public String savePurchaseInvoice(@ModelAttribute("newPurchaseInvoice")InvoiceDTO invoiceDTO,Model model){
        invoiceService.save(invoiceDTO);
        return "/invoice/purchase-invoice-update";
    }


    @GetMapping("/update/{id}")
    public String editPurchaseInvoices(@PathVariable("id") Long id, Model model){
        model.addAttribute("invoice",invoiceService.findById(id));
       // model.addAttribute("vendors", clientVendorService.listAllClientVendor());
       // model.addAttribute("products", productService.listAllProducts());
    return "/invoice/purchase-invoice-update";

   }
    @PostMapping("/update")
    public String updatePurchaseInvoices(@ModelAttribute("invoice")InvoiceDTO invoice, Model model){
        invoiceService.update(invoice);
        return "redirect:/purchaseInvoices/list";

    }

   @GetMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id")Long id, Model model){
        model.addAttribute("newInvoiceProduct",new InvoiceProductDTO());
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
    return "redirect:/purchaseInvoices/update";

    }

    @GetMapping("/delete/{id}")
    public String deletePurchaseInvoice(@PathVariable("id") Long id){
        invoiceService.delete(id);
        return "redirect:/purchaseInvoices/list";
    }
    @GetMapping("/approve/{id}")
    public String getApproved(@PathVariable("id")Long id){
        invoiceService.approve(id);
        return "redirect:/purchaseInvoices/list";
    }
    //@GetMapping("/print/{id}")
    @GetMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public String deleteInvoiceProduct(@PathVariable("invoiceId")Long invoiceId,@PathVariable("invoiceProductId")Long invoiceProductId){
        invoiceProductService.delete(invoiceId,invoiceProductId);
        return "redirect:/purchaseInvoices/update";
    }


}
