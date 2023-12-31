package com.cydeo.entity;

import com.cydeo.enums.ClientVendorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "clients_vendors")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted = false")
public class ClientVendor extends BaseEntity {
    private String clientVendorName;
    private String phone;
    private String website;
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;
    @OneToOne(cascade = {CascadeType.ALL})
    private Address address;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

}
