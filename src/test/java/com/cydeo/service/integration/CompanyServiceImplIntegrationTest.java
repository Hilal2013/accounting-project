package com.cydeo.service.integration;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.exception.CompanyNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class CompanyServiceImplIntegrationTest {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MapperUtil mapperUtil;

    @Test
    @Transactional
    void should_find_company_by_id() {
        CompanyDTO companyDTO = companyService.findById(1L);
        assertNotNull(companyDTO);
        assertEquals("CYDEO", companyDTO.getTitle());
    }
    @Test
    void should_throw_exception_notExistCompany_byLoggedInUser() {
        Throwable throwable = catchThrowable( () -> companyService.findById(0L));
        assertInstanceOf(CompanyNotFoundException.class, throwable);
        assertEquals("Company couldn't find." , throwable.getMessage());
    }
    @Test
    void should_activate(){
        CompanyDTO companyDTO = new CompanyDTO();
       companyDTO .setId(1L);
        companyService.changeCompanyStatus(companyDTO.getId(),CompanyStatus.PASSIVE);
        Company company = companyRepository.findById(1L)
                .orElseThrow(()-> new CompanyNotFoundException("Company not found"));
        assertThat(company.getCompanyStatus().equals(CompanyStatus.ACTIVE));
    }
    @Test
    void should_deactivate(){
        CompanyDTO companyDTO = new CompanyDTO();
         companyDTO .setId(1L);
        companyService.changeCompanyStatus(companyDTO.getId(),CompanyStatus.ACTIVE);
        Company company = companyRepository.findById(1L)
                .orElseThrow(()-> new CompanyNotFoundException("Company not found"));
        assertThat(company.getCompanyStatus().equals(CompanyStatus.PASSIVE));
    }

    @Test
    @Transactional
    @WithMockUser(username = "root@cydeo.com", password = "Abc1", roles = "Root User")
    void should_find_all_companies() {
        List<CompanyDTO> listDTO = companyService.getListOfCompanies();
        List<String> expectedTitles = List.of("Blue Tech", "Green Tech", "Red Tech");
        List<String> actualTitles = listDTO.stream().map(dto->dto.getTitle()).collect(Collectors.toList());
        assertThat(expectedTitles).isEqualTo(actualTitles);
    }


}
//    CompanyDTO companyDTO = new CompanyDTO();
//        companyDTO.setTitle("TechTurtles");
//        companyDTO  .setWebsite("www.techTurtles.com");
//       companyDTO .setId(1L);
//        companyDTO .setPhone("123456789");
//        companyDTO .setAddress(new AddressDTO());