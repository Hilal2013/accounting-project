package com.cydeo.service.unit;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Address;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
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

import static org.assertj.core.api.Assertions.assertThat;
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
        companyDTO.getAddress().setCountry("China");

    }

    @Test
    void should_find_company_by_id() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
        CompanyDTO actualCompanyDTO = companyService.findById(company.getId());
        assertNotNull(actualCompanyDTO);
        assertEquals(company.getId(), actualCompanyDTO.getId());
    }

    @Test
    void should_throw_exception_when_company_cannot_find() {

        when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class, () -> companyService.findById(company.getId()));
//        Throwable throwable = catchThrowable( () -> companyService.findById(0L));
//        assertInstanceOf(CompanyNotFoundException.class, throwable);
//        assertEquals("Company couldn't find." , throwable.getMessage());
    }

    @Test
    void should_find_companyDTo_by_loggedUser() {
        User loggedInUser=new User();
      loggedInUser.setCompany(company);
      loggedInUser.getCompany().setId(company.getId());

       when(securityService.getLoggedInUser()).thenReturn(mapperUtil.convert(loggedInUser,new UserDTO()));
        when(companyRepository.findById(securityService.getLoggedInUser().getCompany().getId())).thenReturn(Optional.of(company));
        CompanyDTO actualCompanyDTO=companyService.getCompanyDTOByLoggedInUser();
        verify(securityService,times(1)).getLoggedInUser();
        verify(companyRepository,atLeast(1)).findById(company.getId());
        assertEquals(companyDTO,actualCompanyDTO);
    }
    @Test
    void should_list_all_companies() {
        //stub


        }
    @Test
    void should_save_company(){
        company.setCompanyStatus(CompanyStatus.PASSIVE);
when(companyRepository.save(any())).thenReturn(company);
CompanyDTO actualCompanyDTO=companyService.createCompany(companyDTO);
        assertThat(actualCompanyDTO).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(companyDTO);

    }



}