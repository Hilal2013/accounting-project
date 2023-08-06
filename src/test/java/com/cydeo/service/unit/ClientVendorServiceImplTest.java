package com.cydeo.service.unit;
import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.impl.ClientVendorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientVendorServiceImplTest {
    @InjectMocks
    private ClientVendorServiceImpl clientVendorService;

    @Mock
    private ClientVendorRepository clientVendorRepository;

    @Mock
    private CompanyService companyService;
    @Mock
    private MapperUtil mapperUtil;

    @Test
    void shouldReturnFalseWhenCompanyNotExistWithTheName() {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(1L);
        ClientVendorDTO clientVendorDTO = new ClientVendorDTO();
        clientVendorDTO.setClientVendorName("Tech");
        when(companyService.getCompanyDTOByLoggedInUser()).thenReturn(companyDTO);
        Company company = new Company();
        company.setId(1L);
        company.setTitle("Cydeo");
        when(mapperUtil.convert(any(CompanyDTO.class), any(Company.class))).thenReturn(company);
        when(clientVendorRepository.findByClientVendorNameAndCompany(any(),
                any(Company.class))).thenReturn(null);
        boolean result = clientVendorService.isExistClientVendorByCompanyName(clientVendorDTO);
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenCompanyExistsWithTheNameAndIdsAreDifferent() {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(1L);
        when(companyService.getCompanyDTOByLoggedInUser()).thenReturn(companyDTO);
        Company company = new Company();
        company.setId(1L);
        company.setTitle("Cydeo");
        when(mapperUtil.convert(any(CompanyDTO.class), any(Company.class))).thenReturn(company);
        ClientVendorDTO clientVendorDTO = new ClientVendorDTO();
        clientVendorDTO.setClientVendorName("Tech");
        clientVendorDTO.setId(2L);
        clientVendorDTO.setClientVendorType(ClientVendorType.CLIENT);
        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setId(1L);
        clientVendor.setClientVendorType(ClientVendorType.CLIENT);
        when(clientVendorRepository.findByClientVendorNameAndCompany(any(),
                any(Company.class))).thenReturn(clientVendor);
        boolean result = clientVendorService.isExistClientVendorByCompanyName(clientVendorDTO);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenCompanyExistsWithTheNameAndIdsAreSame() {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(1L);
        when(companyService.getCompanyDTOByLoggedInUser()).thenReturn(companyDTO);
        Company company = new Company();
        company.setId(1L);
        company.setTitle("Cydeo");
        when(mapperUtil.convert(any(CompanyDTO.class), any(Company.class))).thenReturn(company);
        ClientVendorDTO clientVendorDTO = new ClientVendorDTO();
        clientVendorDTO.setClientVendorName("Tech");
        clientVendorDTO.setClientVendorType(ClientVendorType.CLIENT);
        clientVendorDTO.setId(1L);
        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setId(1L);
        clientVendor.setClientVendorType(ClientVendorType.CLIENT);
        when(clientVendorRepository.findByClientVendorNameAndCompany(any(),
                any(Company.class))).thenReturn(clientVendor);
        boolean result = clientVendorService.isExistClientVendorByCompanyName(clientVendorDTO);
        assertFalse(result);
    }

}