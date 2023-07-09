package com.cydeo.service;
import com.cydeo.dto.currency.CurrencyDTO;
import com.cydeo.dto.currency.ExchangeRates;

import java.math.BigDecimal;
import java.util.List;

public interface DashboardService {


   BigDecimal sumOfTotalCost();
   BigDecimal sumOfTotalSales();
   BigDecimal sumOfTotalProfit_Loss();
   ExchangeRates getExchangeRates();


}
