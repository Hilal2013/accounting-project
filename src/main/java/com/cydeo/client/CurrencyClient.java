package com.cydeo.client;

import com.cydeo.dto.currency.CurrencyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(url ="https://cdn.jsdelivr.net",name="currencyApiClient")
public interface CurrencyClient {
    @GetMapping("/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json")
    CurrencyDTO getCurrencyRates();
}