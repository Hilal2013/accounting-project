package com.cydeo.service;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.enums.CompanyStatus;

import java.util.List;

public interface CompanyService {
    CompanyDTO getCompanyDTOByLoggedInUser();

    CompanyDTO findById(Long id);

    List<CompanyDTO> getListOfCompanies();

    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO updateCompany(Long id,CompanyDTO companyDTO);
    void changeCompanyStatus(Long id, CompanyStatus companyStatus);

}
