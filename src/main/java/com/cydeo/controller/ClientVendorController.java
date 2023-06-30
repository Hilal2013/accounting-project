package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {
    private final ClientVendorService clientVendorService;

    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }
    @GetMapping("/list")
    public String getListOfClientVendors(Model model) {
        model.addAttribute("clientVendors", clientVendorService.getListOfClientVendors());
        return "/clientVendor/clientVendor-list";
    }
    @GetMapping("/create")
    public String createClientVendor(Model model){
        model.addAttribute("newClientVendor", new ClientVendorDTO());
        return "clientVendor/clientVendor-create";
    }
    @PostMapping("/create")
    public String saveCompany(@Valid @ModelAttribute("newClientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/clientVendors/create";
        }

        clientVendorService.createClientVendor(clientVendorDTO);
        return "redirect:/clientVendors/list";
    }


}