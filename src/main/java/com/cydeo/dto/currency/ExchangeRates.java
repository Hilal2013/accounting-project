package com.cydeo.dto.currency;

import com.cydeo.client.CurrencyClient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ExchangeRates {

    private BigDecimal euro;
    private BigDecimal britishPound;
    private BigDecimal canadianDollar;
    private BigDecimal japaneseYen;
    private BigDecimal indianRupee;

}
