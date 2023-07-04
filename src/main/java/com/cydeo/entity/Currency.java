package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    private BigDecimal euro= BigDecimal.valueOf(1.09);
    private BigDecimal britishPound= BigDecimal.valueOf(1.27);
    private BigDecimal canadianDollar= BigDecimal.valueOf(0.75);
    private BigDecimal japaneseYen= BigDecimal.valueOf(0.0069);
    private BigDecimal indianRupee= BigDecimal.valueOf(0.012);


}
