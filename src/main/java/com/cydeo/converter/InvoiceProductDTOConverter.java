package com.cydeo.converter;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.service.InvoiceProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProductDTOConverter implements Converter<Long, InvoiceProductDTO> {

    InvoiceProductService invoiceProductService;

    public InvoiceProductDTOConverter(InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceProductDTO convert(Long source) {

        if (source == null) {
            return null;
        }

        return invoiceProductService.findById(source);
    }
}
