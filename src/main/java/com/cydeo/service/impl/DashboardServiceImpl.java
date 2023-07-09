package com.cydeo.service.impl;
import com.cydeo.client.CurrencyClient;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.currency.Eur;
import com.cydeo.dto.currency.ExchangeRates;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.DashboardService;
import com.cydeo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;



@Service
public class DashboardServiceImpl implements DashboardService {
    private final InvoiceService invoiceService;

    private final CurrencyClient currencyClient;

    @Value("${access_key")
    private String accessKey;

    public DashboardServiceImpl(InvoiceService invoiceService, CurrencyClient currencyClient) {
        this.invoiceService = invoiceService;
        this.currencyClient = currencyClient;
    }


    @Override
    public BigDecimal sumOfTotalCost() {

        return invoiceService.listAllInvoice(InvoiceType.PURCHASE).stream()
                .filter(invoiceDTO -> invoiceDTO.getInvoiceStatus() == InvoiceStatus.APPROVED)
                .map(InvoiceDTO::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Override
    public BigDecimal sumOfTotalSales() {
        return invoiceService.listAllInvoice(InvoiceType.SALES).stream()
                .filter(invoiceDTO -> invoiceDTO.getInvoiceStatus() == InvoiceStatus.APPROVED)
                .map(InvoiceDTO::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumOfTotalProfit_Loss() {
        return BigDecimal.valueOf(100.00);
    }

    @Override
    public ExchangeRates getExchangeRates() {
      ExchangeRates exchangeRates = new ExchangeRates();
      exchangeRates.setEuro(BigDecimal.valueOf(currencyClient.getCurrencyData(accessKey, "EUR").getData().getEur().getValue()));
      exchangeRates.setBritishPound(BigDecimal.valueOf(currencyClient.getCurrencyData(accessKey,"GBP").getData().getGbp().getValue()));
      exchangeRates.setCanadianDollar(BigDecimal.valueOf(currencyClient.getCurrencyData(accessKey,"CAD").getData().getCad().getValue()));
      exchangeRates.setJapaneseYen(BigDecimal.valueOf(currencyClient.getCurrencyData(accessKey,"JPY").getData().getJpy().getValue()));
      exchangeRates.setIndianRupee(BigDecimal.valueOf(currencyClient.getCurrencyData(accessKey,"INR").getData().getInr().getValue()));
      return exchangeRates;










    }
}
