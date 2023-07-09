package com.cydeo.client;

import com.cydeo.dto.country.CountryDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url="https://restcountries.com",name="COUNTRY-CLIENT")
public interface CountryClient {
   // https://www.universal-tutorial.com/api/countries

   @GetMapping("//v3.1/all")
   List<CountryDTO> getCountryList();
}
