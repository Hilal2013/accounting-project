package com.cydeo.service.unit;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.impl.ClientVendorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClientVendorServiceImplTest {
    @InjectMocks
    private ClientVendorServiceImpl clientVendorService;

    @Mock
    private ClientVendorRepository clientVendorRepository;

    @Mock
    private CompanyService companyService;
    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());
    // if you create object we will use spy
    //this is the object we use//spy=>we are not mocking this is actual one
    //we dont wanna mock mapper //because spring create beans//
    ClientVendor clientVendor;
    ClientVendorDTO clientVendorDTO;

    @BeforeEach
    void setUp() {
        clientVendorDTO = new ClientVendorDTO();
        clientVendorDTO.setId(1L);
        clientVendorDTO.setClientVendorType(ClientVendorType.CLIENT);
        clientVendorDTO .setClientVendorName("Tech");
        //  clientVendorDTO  .setAddress(new AddressDTO());
        clientVendorDTO.setWebsite("https://www.tech.com");
        clientVendorDTO.setPhone("123456");

        clientVendor = mapperUtil.convert(clientVendorDTO,new ClientVendor());
        clientVendor .setId(1L);
        clientVendor .setClientVendorType(ClientVendorType.CLIENT);
        clientVendor .setClientVendorName("Tech");
      //  clientVendor  .setAddress(new AddressDTO());
        clientVendor .setWebsite("https://www.tech.com");
        clientVendor.setPhone("123456");

    }
//    @Test
//    void should_find_clientVendor_by_id() {
//
//        when(clientVendorRepository.findById(clientVendor.getId())).thenReturn(Optional.of(clientVendor));
//        ClientVendorDTO actualClientVendorDTO = clientVendorService.findById(1L);
//        assertThat(actualClientVendorDTO.getId()).isEqualTo(clientVendor.getId());
//
//    }
//
//    @Test
//    void should_throw_exception_when_client_vendor_not_found() {
//        Throwable throwable = catchThrowable( () -> clientVendorService.findById(0L));
//        assertInstanceOf(RuntimeException.class, throwable);
//        assertEquals("ClientVendor couldn't find." , throwable.getMessage());
//    }



}