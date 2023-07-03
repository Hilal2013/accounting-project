package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientVendorDTO {
    private Long id;
    @NotBlank(message = "This is a required field.")
    private String clientVendorName;
    private String phone;
    private String website;
    private ClientVendorType clientVendorType;
    private AddressDTO address;
    private CompanyDTO companyDTO;
}