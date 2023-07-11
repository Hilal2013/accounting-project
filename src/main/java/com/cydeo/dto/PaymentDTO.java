package com.cydeo.dto;

import com.cydeo.enums.Months;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Integer year;
    private Months month;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private boolean isPaid;
    private String companyStripedId;
    private String description;  //  (only for DTO)
    private CompanyDTO company;

}
