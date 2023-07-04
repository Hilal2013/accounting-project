package com.cydeo.controller;

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
@RequestMapping("/salesInvoices")
public class SalesInvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;
    private final ClientVendorService clientVendorService;
    private final CompanyService companyService;

    public SalesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ProductService productService, ClientVendorService clientVendorService, CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
        this.clientVendorService = clientVendorService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listAllSalesInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.listAllInvoice(InvoiceType.SALES));
        return "invoice/sales-invoice-list";
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model) {
        model.addAttribute("newSalesInvoice", invoiceService.createNewSalesInvoice());
        model.addAttribute("clients", clientVendorService.listAllClientVendor(ClientVendorType.CLIENT));
        return "invoice/sales-invoice-create";
    }

    @PostMapping("/create")
    public String saveSalesInvoice(@Valid @ModelAttribute("newSalesInvoice") InvoiceDTO invoice, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", clientVendorService.listAllClientVendor(ClientVendorType.CLIENT));
            return "invoice/sales-invoice-create";
        }
        invoiceService.save(invoice, InvoiceType.SALES);
        String id = invoiceService.findInvoiceId();
        return "redirect:/salesInvoices/update/"+id;
    }

    @GetMapping("/update/{id}")
    public String editSalesInvoice(@PathVariable Long id, Model model) {
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("clients", clientVendorService.listAllClientVendor(ClientVendorType.CLIENT));
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("products", productService.listAllProducts());
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
        return "/invoice/sales-invoice-update";
    }

    @PostMapping("/update/{id}")
    public String updateSalesInvoice(@PathVariable("id") Long id,@ModelAttribute("newPurchaseInvoice")InvoiceDTO invoice) {
        invoiceService.save(invoice,InvoiceType.SALES);
        invoiceService.createNewSalesInvoice();
        return "redirect:/salesInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{invoiceId}")
    public String addInvoiceProduct1(@PathVariable("invoiceId") Long id,
                                     @Valid @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO,
                                     BindingResult bindingResult, Model model) {
        if (productService.checkQuantity(invoiceProductDTO)) {
            bindingResult.rejectValue("quantity", "",
                    "Not enough " + "<" + invoiceProductDTO.getProduct().getName() + ">" + " quantity to sell...");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("invoice", invoiceService.findById(id));
            model.addAttribute("clients", clientVendorService.listAllClientVendor(ClientVendorType.CLIENT));
            model.addAttribute("products", productService.listAllProducts());
            return "/invoice/sales-invoice-update";
        }
        invoiceProductService.save(invoiceProductDTO, id);
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
        return "redirect:/salesInvoices/update/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteSalesInvoiceById(@PathVariable("id") Long id) {
        invoiceService.delete(id);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/approve/{id}")
    public String approveSalesInvoiceById(@PathVariable Long id) {
        invoiceService.approve(id);
        return "redirect:/salesInvoices/list";
    }



    @GetMapping("/removeInvoiceProduct/{invoiceId1}/{invoiceProductId}")
    public String removeInvoiceProduct(@PathVariable("invoiceId1") Long id, @PathVariable("invoiceProductId") Long invoiceProductId) {
        invoiceProductService.delete(invoiceProductId);
        return "redirect:/salesInvoices/update/"+id;
    }

    @GetMapping("/print/{invoiceId}")
    public String removeInvoice(@PathVariable("invoiceId") Long invoiceId, Model model){
        model.addAttribute("invoice",invoiceService.findById(invoiceId));
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(invoiceId));
        model.addAttribute("company", companyService.getCompanyDTOByLoggedInUser());
        return "invoice/invoice_print";
    }


}
