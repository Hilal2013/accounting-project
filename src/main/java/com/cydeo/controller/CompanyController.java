package com.cydeo.controller;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String getListOfCompanies(Model model) {
        model.addAttribute("companies", companyService.getListOfCompanies());
        return "company-list";
    }

    @GetMapping("/create")
    public String createCompany(Model model){
        model.addAttribute("newCompany",new CompanyDTO());
           return"company-create";
    }

    @PostMapping("/create")
    public String saveCompany(@Valid @ModelAttribute("newCompany") CompanyDTO companyDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/companies/create";
        }

        companyService.createCompany(companyDTO);
        return "redirect:/companies/list";
    }

    @GetMapping("/update/{id}")
    public String editCompanyForm(@PathVariable("id") Long id,Model model) {
        model.addAttribute("company",companyService.findById(id));
        return "company-update";
    }

    @PostMapping("/update{id}")
    public String updateCompany(@PathVariable("id") Long id,@Valid @ModelAttribute("company") CompanyDTO companyDTO,BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "company-update";
        }
        companyService.updateCompany(id,companyDTO);
        return "redirect:/companies/list";
    }
// Activate and Deactivate functions
    @PostMapping("/activate/{id}")
    public String activateCompany(@PathVariable Long id) {
        companyService.changeCompanyStatus(id, CompanyStatus.ACTIVE);
        return "redirect:/companies/list";
    }

    @PostMapping("/deactivate/{companyId}")
    public String deactivateCompany(@PathVariable Long companyId) {
        companyService.changeCompanyStatus(companyId, CompanyStatus.PASSIVE);
        return "redirect:/companies/list";
    }


}
