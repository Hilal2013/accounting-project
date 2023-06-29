package com.cydeo.entity;


import com.cydeo.enums.CompanyStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="companies")
@Getter
@Setter
@NoArgsConstructor
public class Company extends BaseEntity{
    @Column(unique = true)
    private String title;
    private String phone;
    private String website;
    @OneToOne(cascade = {CascadeType.ALL})
    private Address address;
    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;
}
