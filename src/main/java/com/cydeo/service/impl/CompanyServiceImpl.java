package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        Company company = companyRepository.findById
                        (securityService.getLoggedInUser().getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company couldn't be found."));
        return mapperUtil.convert(company, new CompanyDTO());

    }

    @Override
    public CompanyDTO findById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company couldn't find.")), new CompanyDTO());
    }

    @Override
    public List<CompanyDTO> getListOfCompanies() {

        if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")){

            return companyRepository.getCompaniesSortedByStatusAndTitle()
                    .stream()
                    .filter(company -> company.getId() != 1)
                    .map(company -> mapperUtil.convert(company, new CompanyDTO()))
                    .collect(Collectors.toList());
        }else{
            return companyRepository.findAllByTitle(getCompanyDTOByLoggedInUser().getTitle())
                    .stream()
                    .map(company -> mapperUtil.convert(company, new CompanyDTO()))
                    .collect(Collectors.toList());

        }


    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = companyRepository.save(mapperUtil.convert(companyDTO, new Company()));
        //New created company's company status will be "Passive" as default
        company.setCompanyStatus(CompanyStatus.PASSIVE); // Set
        companyRepository.save(company);
        return mapperUtil.convert(company, new CompanyDTO());
    }

    @Override
    public CompanyDTO updateCompany(Long id,CompanyDTO companyDTO) {
        //find Company entity
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company couldn't find."));
        //convert coming CompanyDTO to Company
        Company convertedCompany = mapperUtil.convert(companyDTO, new Company());
        convertedCompany.setId(company.getId());
        convertedCompany.setCompanyStatus(company.getCompanyStatus());
        companyRepository.save(convertedCompany);
        return mapperUtil.convert(convertedCompany,new CompanyDTO());

    }

    @Override
    public void changeCompanyStatus(Long id, CompanyStatus companyStatus) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company couldn't find."));

        company.setCompanyStatus(companyStatus);
        companyRepository.save(company);
    }

    @Override
    public boolean existByTitle(CompanyDTO companyDTO) {
        Company company =  companyRepository.findByTitle(companyDTO.getTitle());
        if (company == null) return false;
     return company.getTitle().equals(companyDTO.getTitle());
    }
}
//  CompanyStatus loggedInUserCompanyStatus = getCompanyDTOByLoggedInUser().getCompanyStatus();
//            List<Company> companiesByStatus= companyRepository.findAllByCompanyStatusIs(loggedInUserCompanyStatus);
//            return companiesByStatus.stream()
//                    .map(company -> mapperUtil.convert(company, new CompanyDTO()))
//              .collect(Collectors.toList());