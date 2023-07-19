package com.cydeo.service.unit;

import com.cydeo.dto.AddressDTO;
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
        companyDTO = new CompanyDTO();
        companyDTO .setId(1L);
        companyDTO.setTitle("TechTurtles");
        companyDTO  .setWebsite("www.techTurtles.com");
        companyDTO .setPhone("123456789");
        companyDTO.setCompanyStatus(CompanyStatus.PASSIVE);
       //   companyDTO.getAddress().setCountry("China");
        company = mapperUtil.convert(companyDTO,new Company());
        company.setId(1l);
        companyDTO.setTitle("TechTurtles");
        companyDTO  .setWebsite("www.techTurtles.com");
        companyDTO .setPhone("123456789");
        company.setCompanyStatus(CompanyStatus.PASSIVE);
     //   company.getAddress().setCountry("China");


    }

    @Test
    void should_find_company_by_id() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
        CompanyDTO actualCompanyDTO = companyService.findById(company.getId());
        assertNotNull(actualCompanyDTO);
        assertEquals(company.getId(), actualCompanyDTO.getId());
    }

    @Test
    void should_throw_exception_when_company_not_exist() {

//        when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());
//        assertThrows(CompanyNotFoundException.class, () -> companyService.findById(company.getId()));
        Throwable throwable = catchThrowable( () -> companyService.findById(0L));
        assertInstanceOf(CompanyNotFoundException.class, throwable);
        assertEquals("Company couldn't find." , throwable.getMessage());
    }
//   @Test
//    void should_save_company(){
//
//        when(companyRepository.save(any())).thenReturn(company);
//        CompanyDTO actualCompanyDTO=companyService.createCompany(companyDTO);
//        assertThat(actualCompanyDTO).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(companyDTO);
//
//    }


//    @Test
//    void should_find_companyDT0_by_loggedUser() {
//       UserDTO loggedInUser=new UserDTO();
//        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);
//        CompanyDTO actualCompanyDTO=companyService.getCompanyDTOByLoggedInUser();
//                assertEquals(companyDTO.getId(),actualCompanyDTO.getId());
//    }





}