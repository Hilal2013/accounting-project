package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ExchangeRates {

    private BigDecimal euro;
    private BigDecimal britishPound;
    private BigDecimal canadianDollar;
    private BigDecimal japaneseYen;
    private BigDecimal indianRupee;


}
