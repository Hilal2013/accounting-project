package com.cydeo.client;
import com.cydeo.dto.currency.CurrencyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url ="https://api.currencyapi.com",name = "currencyApiClient")
public interface CurrencyClient {
//https://api.currencyapi.com/v3/currencies?apikey=03TLVNVWE5fXgXqwtoaefAPylpQI3uMheW0sTE5o&currencies=EUR%2CUSD%2CCAD%2CJPY%2CINR

    @GetMapping("/v3/currencies")
    CurrencyDTO getCurrencyData(@RequestParam("apikey")String accessKey, @RequestParam("currencies") String currencies);

}
