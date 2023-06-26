package com.cydeo.dto;


import com.cydeo.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long id;
    private String description;


}
