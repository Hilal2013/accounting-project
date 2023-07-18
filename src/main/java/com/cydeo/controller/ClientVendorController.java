package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {
    private final ClientVendorService clientVendorService;
private final CompanyService companyService;
    public ClientVendorController(ClientVendorService clientVendorService, CompanyService companyService) {
        this.clientVendorService = clientVendorService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String getListOfClientVendors(Model model) {
        model.addAttribute("clientVendors", clientVendorService.getListOfClientVendors());
        return "/clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String createClientVendor(Model model) {
        model.addAttribute("newClientVendor", new ClientVendorDTO());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries", companyService.retrieveCountryList());
        return "clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String saveCompany(@Valid @ModelAttribute("newClientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult, Model model) {

        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries", companyService.retrieveCountryList());

        // should be unique in current company client/vendors

        if (clientVendorService.isExistClientVendorByCompanyName(clientVendorDTO)) {
            bindingResult.rejectValue("clientVendorName", "",
                    "This Client/Vendor Name already exists.");
        }
        if (bindingResult.hasErrors()) {

            return "/clientVendor/clientVendor-create";
        }

        clientVendorService.createClientVendor(clientVendorDTO);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{id}")
    public String editClientVendorForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("clientVendor", clientVendorService.findById(id));
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("countries", companyService.retrieveCountryList());
        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("update/{id}")
    public String updateClientVendor(@PathVariable("id") Long id, @Valid @ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult, Model model) {
        //TODO Countries will be provided by a third party API by consuming it.
        model.addAttribute("countries", companyService.retrieveCountryList());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        clientVendorDTO.setId(id);
        // should be unique in current company client/vendors
        if (clientVendorService.isExistClientVendorByCompanyName(clientVendorDTO)) {
            bindingResult.rejectValue("clientVendorName", "",
                    "This Client/Vendor in current company already exists.");
        }
        if (bindingResult.hasErrors()) {
            return "clientVendor/clientVendor-update";
        }
        clientVendorService.updateClientVendor(id, clientVendorDTO);
        return "redirect:/clientVendors/list";
    }

    //soft delete
    @GetMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (clientVendorService.isClientVendorHasInvoice(id)) {
redirectAttributes.addFlashAttribute
        ("error", "Client or Vendor has invoice. You cannot delete this ClientVendor");
            return "redirect:/clientVendors/list";

        }
            clientVendorService.delete(id);
        return "redirect:/clientVendors/list";
    }
}