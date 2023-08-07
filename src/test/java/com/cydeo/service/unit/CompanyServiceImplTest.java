package com.cydeo.service.unit;

import com.cydeo.client.CountryClient;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.dto.country.CountryDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.exception.CompanyNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.impl.CompanyServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
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
    @Mock
    private CountryClient countryClient;
    @Mock
    private MapperUtil mapperUtil;

    @Test
    void should_find_company_by_id() {
        Company company = new Company();
        company.setId(1l);
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        when(mapperUtil.convert(any(Company.class), any(CompanyDTO.class))).thenReturn(companyDTO);
        CompanyDTO actualCompanyDTO = companyService.findById(company.getId());
        assertEquals(company.getId(), actualCompanyDTO.getId());
    }

    @Test
    void should_throw_exception_when_company_not_exist() {
        Throwable throwable = catchThrowable(() -> companyService.findById(0L));
        assertInstanceOf(CompanyNotFoundException.class, throwable);
        assertEquals("Company couldn't find.", throwable.getMessage());
    }
   @Test
    void should_save_company(){
       CompanyDTO companyDTO = new CompanyDTO();
       companyDTO.setTitle("Cydeo");
       Company savedCompany = new Company();
       savedCompany.setTitle(companyDTO.getTitle());
       savedCompany.setCompanyStatus(CompanyStatus.PASSIVE);
       when(companyRepository.save(any())).thenReturn(savedCompany);
       when(mapperUtil.convert(any(CompanyDTO.class), any(Company.class))).thenReturn(savedCompany);
       when(mapperUtil.convert(any(Company.class), any(CompanyDTO.class))).thenReturn(companyDTO);
       CompanyDTO actualCompanyDTO=companyService.createCompany(companyDTO);
       assertNotNull(actualCompanyDTO);
       assertEquals(CompanyStatus.PASSIVE, savedCompany.getCompanyStatus());

    }

//        @Test
//    void should_find_companyDT0_by_loggedUser() {
//            UserDTO loggedInUser = new UserDTO();
//            loggedInUser.setId(100L);
//            loggedInUser.setUsername("Hilal");
//
//            Company company = new Company();
//            company.setId(1l);
//            company.setTitle("Cydeo");
//
//           // company.setUser(userService.convertToUser(loggedInUserDTO));
//            // Mock the behavior of the securityService to return the logged-in user
//            when(securityService.getLoggedInUser()).thenReturn(loggedInUser);
//            when(companyService.getCompanyDTOByLoggedInUser()).thenReturn(loggedInUser.getCompany());
//            // Mock the behavior of the repository's findById method
//            when(companyRepository.findById(1l)).thenReturn(Optional.of(company));
//
//            CompanyDTO companyDTO = new CompanyDTO();
//            companyDTO.setId(company.getId());
//            companyDTO.setTitle(company.getTitle());
//            loggedInUser.setCompany(companyDTO);
//
//            when(mapperUtil.convert(any(Company.class), any(CompanyDTO.class))).thenReturn(companyDTO);
//            CompanyDTO actualCompanyDTO=companyService.getCompanyDTOByLoggedInUser();
//                assertEquals(companyDTO.getId(),actualCompanyDTO.getId());
//                assertEquals(companyDTO.getTitle(),actualCompanyDTO.getTitle());
//    }
  //  @Test
//    void shouldRetrieveCountryList() {
//        CountryDTO country1 = new CountryDTO();
//        CountryDTO country2 = new CountryDTO();
//        country1.getName().setCommon("Netherlands");
//        country2.getName().setCommon("Germany");
//        List<CountryDTO> list = new ArrayList<>();
//        list.add(country1);
//        list.add(country2);
//        when(countryClient.getCountryList()).thenReturn(list);
//        List<String> countryList = companyService.retrieveCountryList();
//        assertEquals(2, countryList.size());
//    }



}