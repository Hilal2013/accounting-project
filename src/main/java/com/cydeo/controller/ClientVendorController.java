package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

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
    public String createClientVendor(Model model) {
        model.addAttribute("newClientVendor", new ClientVendorDTO());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        //TODO Countries will be provided by a third party API by consuming it.
        model.addAttribute("countries", List.of("USA", "UK", "Germany"));
        return "clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String saveCompany(@Valid @ModelAttribute("newClientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult, Model model) {

        if (clientVendorService.isExistClientVendorByCompanyName(clientVendorDTO)) {
            bindingResult.rejectValue("clientVendorName", "", "This Client/Vendor Name already exists.");
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
        //TODO Countries will be provided by a third party API by consuming it.
        model.addAttribute("countries", List.of("USA", "UK", "Germany"));
        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("update/{id}")
    public String updateClientVendor(@PathVariable("id") Long id, @Valid @ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult, Model model) {
// should be unique in current company client/vendors
        if (clientVendorService.isExistClientVendorByCompanyName(clientVendorDTO)) {
            bindingResult.rejectValue("clientVendorName", "",
                    "This Client/Vendor in current company already exists.");
        }

        //TODO Countries will be provided by a third party API by consuming it.
        model.addAttribute("countries", List.of("USA", "UK", "Germany"));
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        if (bindingResult.hasErrors()) {
            return "clientVendor/clientVendor-update";
        }
        clientVendorService.updateClientVendor(id, clientVendorDTO);
        return "redirect:/clientVendors/list";
    }

    //soft delete
    @GetMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable("id") Long id) {

        clientVendorService.delete(id);
        return "redirect:/clientVendors/list";
    }
}