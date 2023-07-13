package com.cydeo.service.integration;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.exception.CompanyNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void should_find_company_byLoggedInUser() {
        // Arrange
        User loggedInUser = new User();
        loggedInUser.setCompany(new Company());
        loggedInUser.getCompany().setId(1L);
        when(securityService.getLoggedInUser()).thenReturn(mapperUtil.convert(loggedInUser,new UserDTO()));
        CompanyDTO actualCompanyDTO = companyService.getCompanyDTOByLoggedInUser();
        assertEquals(CompanyDTO.class, actualCompanyDTO.getClass());
    }

    @Test
    void should_throw_exception_notExistCompany_byLoggedInUser() {
        // Arrange
        User loggedInUser = new User();
        loggedInUser.setCompany(new Company());
        loggedInUser.getCompany().setId(1L);
        when(securityService.getLoggedInUser()).thenReturn((mapperUtil.convert(loggedInUser,new UserDTO())));
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class, () -> companyService.getCompanyDTOByLoggedInUser());
    }


}
