package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchasesInvoiceController {

    @GetMapping("/create")
    public String cratePurchaseInvoices(Model model){


        return "/invoice/purchase-invoice-create";
    }
    @GetMapping("/list")
    public String listAllPurchaseInvoices(){

        return "/invoice/purchase-invoice-list";
    }
    @PostMapping("/update/{id}")
    public String updatePurchaseInvoices(@PathVariable("id") Long id){
        return "/invoice/purchase-invoice-update";

   }

}
