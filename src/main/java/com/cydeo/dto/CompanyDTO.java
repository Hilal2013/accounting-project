package com.cydeo.dto;

import com.cydeo.enums.CompanyStatus;
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

public class CompanyDTO {
    private Long id;
    @NotBlank(message = "Title is a required field.")
    @Size(min = 2, max = 100, message = "Title should be 2-100 characters long.")
    private String title;
    @NotBlank(message = "Phone Number is a required field and may be in any valid phone number format.")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
            message = "Phone Number is required field and may be in any valid phone number format.")
    private String phone;
    @NotBlank(message = "Website is a required field.")
    @NotBlank(message = "Website is a required field")
    @Pattern(regexp = "^(http:\\/\\/|https:\\/\\/)?(www\\.)?[a-zA-Z0-9-_\\.]+" +
            "\\.[a-zA-Z]+(:\\d+)?(\\/[a-zA-Z\\d\\.\\-_]*)*[a-zA-Z.!@#$%&=-_'\":,.?\\d*)(]*$"
            , message = "Website should have a valid format.")
    private String website;
    @Valid
    private AddressDTO address;
    private CompanyStatus companyStatus;
}
