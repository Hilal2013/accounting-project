package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientVendorDTO {
    private Long id;
    @NotBlank(message = "Client/Vendor Name is a required field.")
    @Size(min = 2, max = 50, message ="Client/Vendor Name should be 2-50 characters long.")
    private String clientVendorName;
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
            message = "Phone Number is required field and may be in any valid phone number format.")
    private String phone;
    @Pattern(regexp = "^(http:\\/\\/|https:\\/\\/)?(www\\.)?[a-zA-Z0-9-_\\.]+" +
            "\\.[a-zA-Z]+(:\\d+)?(\\/[a-zA-Z\\d\\.\\-_]*)*[a-zA-Z.!@#$%&=-_'\":,.?\\d*)(]*$"
            , message = "Website should have a valid format.")
    private String website;
   @NotNull(message = "Please select type.")
    private ClientVendorType clientVendorType;
    @Valid
    private AddressDTO address;
    private CompanyDTO companyDTO;
}
//   @Pattern(regexp = "^http(s {0,1})://[a-zA-Z0-9/\\-\\.]+.([A-Za-z/] {2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*"
//            ,message = "Website should have a valid format.")
