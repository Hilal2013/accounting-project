package com.cydeo.converter;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.service.CompanyService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationPropertiesBinding
public class CompanyDTOConverter implements Converter<String, CompanyDTO> {

    CompanyService companyService;

    public CompanyDTOConverter(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public CompanyDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }

        return companyService.findById(Long.parseLong(source));
    }
}
