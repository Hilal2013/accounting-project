package com.cydeo.service.integration;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClientVendorServiceImplIntegrationTest {
    @Autowired
    private ClientVendorService clientVendorService;
    @Autowired
   private ClientVendorRepository clientVendorRepository;

    @Test
    void should_find_clientVendor_by_id() {

        ClientVendorDTO clientVendorDTO = clientVendorService.findById(1L);
        // then
        assertNotNull(clientVendorDTO);
        assertThat(clientVendorDTO.getId()).isEqualTo(1l);
        assertEquals("Orange Tech", clientVendorDTO.getClientVendorName());
    }
    @Test
    void should_throw_exception_when_clientVendor_not_exist() {
        Throwable throwable = catchThrowable( () -> clientVendorService.findById(0L));
        assertInstanceOf(RuntimeException.class, throwable);
        assertEquals("ClientVendor couldn't find." , throwable.getMessage());
    }
}
