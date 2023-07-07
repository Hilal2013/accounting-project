package com.cydeo.client;

import com.cydeo.dto.CountryDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Headers({
    "Content-Type: application/json",
    "Authorization: Bearer {authToken}"
})
@FeignClient(url="https://www.universal-tutorial.com/api",name="COUNTRY-CLIENT")
public interface CountryClient {
   // https://www.universal-tutorial.com/api/countries

   @GetMapping("/countries")
   List<CountryDTO> getCountryList(@Param("authToken") String authToken);
}
