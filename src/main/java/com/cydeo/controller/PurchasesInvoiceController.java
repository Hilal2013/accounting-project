package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;
    private final ClientVendorService clientVendorService;
    private final CompanyService companyService;

    public PurchasesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ProductService productService, ClientVendorService clientVendorService, CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
        this.clientVendorService = clientVendorService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listAllPurchaseInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.listAllInvoice(InvoiceType.PURCHASE));
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String cratePurchaseInvoices(Model model) {
        model.addAttribute("newPurchaseInvoice", invoiceService.createNewPurchasesInvoice());
        model.addAttribute("vendors", clientVendorService.listAllClientVendor(ClientVendorType.VENDOR));
        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String savePurchaseInvoice(@Valid @ModelAttribute("newPurchaseInvoice") InvoiceDTO invoice,BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("vendors", clientVendorService.listAllClientVendor(ClientVendorType.VENDOR));
            return "/invoice/purchase-invoice-create";
        }
        invoiceService.save(invoice, InvoiceType.PURCHASE);
        String id = invoiceService.findInvoiceId();
        return "redirect:/purchaseInvoices/update/" + id;
    }

    @GetMapping("/update/{id}")
    public String editPurchaseInvoices(@PathVariable("id") Long id, Model model) {
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.listAllClientVendor(ClientVendorType.VENDOR));
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.listAllProducts());
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
        return "/invoice/purchase-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updateInvoice(@PathVariable("id") Long id, @ModelAttribute("newPurchaseInvoice") InvoiceDTO invoice) {
        invoiceService.save(invoice,InvoiceType.PURCHASE);
        invoiceService.createNewPurchasesInvoice();
        return "redirect:/purchaseInvoices/list";
    }


    @PostMapping("/addInvoiceProduct/{invoiceId}")
    public String addInvoiceProduct1(@PathVariable("invoiceId") Long id,@Valid @ModelAttribute InvoiceProductDTO invoiceProductDTO,BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
//            model.addAttribute("vendors", clientVendorService.listAllClientVendor(ClientVendorType.VENDOR));
//            model.addAttribute("products", productService.listAllProducts());
            return "redirect:/purchaseInvoices/update/"+id;
        }
        invoiceProductService.save(invoiceProductDTO, id);
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
        return "redirect:/purchaseInvoices/update/" + id;

    }


    @GetMapping("/delete/{id}")
    public String deletePurchaseInvoice(@PathVariable("id") Long id) {
        invoiceService.delete(id);
        return "redirect:/purchaseInvoices/list";
    }

    @GetMapping("/approve/{id}")
    public String getApproved(@PathVariable("id") Long id) {
        invoiceService.approve(id);
        return "redirect:/purchaseInvoices/list";
    }
    @GetMapping("/print/{invoiceId}")
        public String removeInvoice(@PathVariable("invoiceId") Long invoiceId, Model model){
            model.addAttribute("invoice",invoiceService.findById(invoiceId));
            model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(invoiceId));
            model.addAttribute("company", companyService.getCompanyDTOByLoggedInUser());
            return "invoice/invoice_print";
        }


    @GetMapping("/removeInvoiceProduct/{invoiceId1}/{invoiceProductId}")
    public String deleteInvoiceProduct(@PathVariable("invoiceId1") Long id, @PathVariable("invoiceProductId") Long invoiceProductId) {
        invoiceProductService.delete(invoiceProductId);

        return "redirect:/purchaseInvoices/update/"+id;
    }

}

