package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Long id;

    @NotBlank(message="Description is a required field.")
    @NotNull
    @Size(max=100, min=2, message = "Description should have 2-100 characters long.")
    private String description;

    private CompanyDTO company;
    private boolean hasProduct;

}
