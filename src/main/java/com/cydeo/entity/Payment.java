package com.cydeo.entity;

import com.cydeo.enums.Months;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="payments")
public class Payment extends BaseEntity{

    private int year;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private boolean isPaid;
    private String companyStripedId;//(to store stripe id's about payments)
    private Months month;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


}
