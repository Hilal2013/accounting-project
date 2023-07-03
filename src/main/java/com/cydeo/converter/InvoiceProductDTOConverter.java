package com.cydeo.converter;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.service.InvoiceProductService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationPropertiesBinding
public class InvoiceProductDTOConverter implements Converter<String, InvoiceProductDTO> {

    InvoiceProductService invoiceProductService;

    public InvoiceProductDTOConverter(InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceProductDTO convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return invoiceProductService.findById(Long.parseLong(source));
    }

}
