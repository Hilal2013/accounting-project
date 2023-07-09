package com.cydeo.service;
import com.cydeo.dto.ExchangeRates;
import com.cydeo.dto.currency.CurrencyDTO;

import java.math.BigDecimal;


public interface DashboardService {


   BigDecimal sumOfTotalCost();
   BigDecimal sumOfTotalSales();
   BigDecimal sumOfTotalProfit_Loss();
   ExchangeRates getRates();


}
