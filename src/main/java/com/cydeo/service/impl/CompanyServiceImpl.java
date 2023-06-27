package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Company;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public CompanyDTO getCompanyDTOByLoggedInUser() {

        //find UserDTO who logged in and find company through id //convert Company entity to CompanyDTO
      Company company= companyRepository.findById
              (securityService.getLoggedInUser().getCompany().getId())
              .orElseThrow(()->new RuntimeException("Company couldn't be found."));
      return mapperUtil.convert(company,new CompanyDTO());

    }
}
