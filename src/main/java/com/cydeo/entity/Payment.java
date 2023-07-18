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
    @Enumerated(EnumType.STRING)
    private Months month;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Payment(int year, BigDecimal amount, LocalDate paymentDate, boolean isPaid, Months month, Company company) {
        this.year = year;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
        this.month = month;
        this.company = company;
    }
}
