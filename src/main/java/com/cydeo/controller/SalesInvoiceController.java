package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.service.ClientVendorService;
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
    private final ClientVendorService clientVendorService;

    public SalesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
//        this.productService = invoiceProductService;
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listAllSalesInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.listAllInvoice());
        return "invoice/sales-invoice-list";
    }

    @GetMapping("/create")
    public String createSalesInvoice(Model model) {
        model.addAttribute("newSalesInvoice", invoiceService.createNewSalesInvoice());
        model.addAttribute("clients", clientVendorService.getListOfClientVendors());
        return "invoice/sales-invoice-create";
    }

    @PostMapping("create")
    public String saveSalesInvoice(@ModelAttribute InvoiceDTO invoice) {
        invoiceService.save(invoice);
        return "invoice/sales-invoice-update";
    }

    @GetMapping("/update/{id}")
    public String editSalesInvoice(@PathVariable Long id, Model model) {
        model.addAttribute("invoice", invoiceService.findById(id));
//        model.addAttribute("clients", clientVendorService.listAllClientVendor());
        model.addAttribute("newInvoiceProduct", invoiceProductService.findByInvoiceId(id));
//        model.addAttribute("products", productService.listAllProducts());
    //    model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct());
        return "invoice/sales-invoice-update";
    }

    @PostMapping("/update")
    public String updateSalesInvoice(@ModelAttribute("invoice")InvoiceDTO invoice) {
        invoiceService.update(invoice);
        return "redirect:invoice/sales-invoice-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSalesInvoiceById(@PathVariable Long id) {
        invoiceService.delete(id);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("/approve/{id}")
    public String approveSalesInvoiceById(@PathVariable Long id) {
        invoiceService.approve(id);
        return "redirect:/salesInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable Long id,
                                    @ModelAttribute("newInvoiceProduct") InvoiceProductDTO invoiceProductDTO) {
        invoiceProductService.save(invoiceProductDTO, id);
        return "redirect:/salesInvoices/list";
    }

    @PostMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProuductId}")
    public String removeInvoiceProduct(@PathVariable Long invoiceId, @PathVariable Long invoiceProuductId) {
        invoiceProductService.delete(invoiceId, invoiceProuductId);
        return "redirect:/salesInvoices/list";
    }

    @GetMapping("print/{id}")
    public String printSalesInvoice(@PathVariable Long id) {
//        invoiceService.print(id);
        return "invoice/invoice_print";
    }


}
