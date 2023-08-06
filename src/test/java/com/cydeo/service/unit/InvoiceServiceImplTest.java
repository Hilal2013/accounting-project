package com.cydeo.service.unit;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.Invoice;
import com.cydeo.exception.InvoiceNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceImplTest {
    @Mock
    private InvoiceProductService invoiceProductService;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Test
    void shouldThrowExceptionWhenInvoiceNotExist(){
        Throwable throwable = catchThrowable(() -> invoiceService.findById(0L));
        assertInstanceOf(InvoiceNotFoundException.class, throwable);
        assertEquals("Invoice does not exist!.", throwable.getMessage());

    }

    @Test
    void shouldCalculateInvoiceWhenInvoiceExists(){
        Invoice invoice = new Invoice();
        invoice.setId(1L);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        when(invoiceRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));
        when(mapperUtil.convert(any(Invoice.class), any(InvoiceDTO.class))).thenReturn(invoiceDTO);

        InvoiceProductDTO p1 = new InvoiceProductDTO();
        InvoiceProductDTO p2 = new InvoiceProductDTO();
        p1.setQuantity(5);
        p1.setPrice(BigDecimal.TEN);
        p1.setTotal(BigDecimal.valueOf(60));

        p2.setQuantity(7);
        p2.setPrice(BigDecimal.valueOf(12));
        p2.setTotal(BigDecimal.valueOf(90));

        List<InvoiceProductDTO> invoiceProductDTOS = new ArrayList<>();
        invoiceProductDTOS.add(p1);
        invoiceProductDTOS.add(p2);

        when(invoiceProductService.listAllInvoiceProduct(1L))
                .thenReturn(invoiceProductDTOS);

//        InvoiceDTO dto = invoiceService.calculateTotal(1L);
//        assertEquals(dto.getTotal(), BigDecimal.valueOf(150));
//        assertEquals(dto.getPrice(), BigDecimal.valueOf(134));
//        assertEquals(dto.getTax(), BigDecimal.valueOf(16));
    }


}
