package com.cydeo.service.unit;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.exception.CompanyNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import org.modelmapper.ModelMapper;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {
    @InjectMocks
    private CompanyServiceImpl companyService;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private SecurityService securityService;

    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());// if you create object we will use spy
    //this is the object we use//spy=>we are not mocking this is actual one
    //we dont wanna mock mapper //because spring create beans//

    Company company;
    CompanyDTO companyDTO;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1l);
        company.setTitle("Cydeo");
        company.setPhone("123456");
        company.setWebsite("abc@gmail.com");
        company.setCompanyStatus(CompanyStatus.PASSIVE);


        companyDTO = new CompanyDTO();

        companyDTO.setId(1l);
        companyDTO.setTitle("Cydeo");
        companyDTO.setPhone("123456");
        companyDTO.setWebsite("abc@gmail.com");
        companyDTO.setCompanyStatus(CompanyStatus.PASSIVE);
    }

    @Test
    void should_find_company_by_id() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
        CompanyDTO actualCompanyDTO=companyService.findById(company.getId());
        assertNotNull(actualCompanyDTO);
        assertEquals(company.getId(), actualCompanyDTO.getId());
    }

    @Test
    void should_throw_exception_when_company_cannot_find() {
        // Mock the repository method
        when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());
        // Call the service method and assert that it throws the expected exception
        assertThrows(CompanyNotFoundException.class, () -> companyService.findById(company.getId()));
    }
}