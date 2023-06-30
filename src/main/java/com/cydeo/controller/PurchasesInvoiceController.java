package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.dto.ProductDTO;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;
    private final ClientVendorService clientVendorService;

    public PurchasesInvoiceController(InvoiceService invoiceService, InvoiceProductService invoiceProductService, ProductService productService, ClientVendorService clientVendorService) {
        this.invoiceService = invoiceService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
        this.clientVendorService = clientVendorService;
    }

    @GetMapping("/list")
    public String listAllPurchaseInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.listAllInvoice());
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String cratePurchaseInvoices(Model model) {
        model.addAttribute("newPurchaseInvoice", invoiceService.createNewPurchasesInvoice());
        model.addAttribute("vendors", clientVendorService.listAllClientVendor(ClientVendorType.VENDOR));
        // model.addAttribute("vendors",clientVendorService.listClientVendorType(ClientVendorType.VENDOR));

        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String savePurchaseInvoice(@ModelAttribute("newPurchaseInvoice") InvoiceDTO invoice,Model model) {
        invoiceService.save(invoice,InvoiceType.PURCHASE);
        String id = invoiceService.findInvoiceId();
        return "redirect:/purchaseInvoices/update/"+id;
    }




    @GetMapping("/update/{id}")
    public String editPurchaseInvoices(@PathVariable("id") Long id, Model model) {
        model.addAttribute("invoice", invoiceService.findById(id));
        model.addAttribute("vendors", clientVendorService.listAllClientVendor(ClientVendorType.VENDOR));
        model.addAttribute("newInvoiceProduct", invoiceProductService.findByInvoiceId(id));
        model.addAttribute("products", productService.listAllProducts());
        model.addAttribute("newInvoiceProduct", invoiceProductService.findByInvoiceId(id));
        return "/invoice/purchase-invoice-update";
    }


    @GetMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());

        //  model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
        return "redirect:/purchaseInvoices/update";

    }

    @PostMapping("/addInvoiceProduct/{id}")
    public String addInvoiceProduct1(@PathVariable("id") Long id, @ModelAttribute InvoiceProductDTO invoiceProductDTO, Model model) {
        invoiceProductService.save(invoiceProductDTO, id);
        model.addAttribute("invoiceProducts", invoiceProductService.listAllInvoiceProduct(id));
        return "redirect:/purchaseInvoices/update";

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
}
//    @GetMapping("/print/{id}")
//        public String printPurchasesInvoice (@PathVariable Long id){
//
////        invoiceService.print(id);
//            return "invoice/invoice_print";
//
//        }
//    }
//    @PostMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
//    public String deleteInvoiceProduct(@PathVariable("invoiceId")Long invoiceId,@PathVariable("invoiceProductId")Long invoiceProductId){
//        invoiceProductService.delete(invoiceId,invoiceProductId);
//        return "redirect:/purchaseInvoices/update";
//    }



