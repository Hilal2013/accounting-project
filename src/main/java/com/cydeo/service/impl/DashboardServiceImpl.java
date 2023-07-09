package com.cydeo.service.impl;
import com.cydeo.client.CurrencyClient;
import com.cydeo.dto.ExchangeRates;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.currency.CurrencyDTO;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.DashboardService;
import com.cydeo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;


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
    public ExchangeRates getRates() {
        ExchangeRates exchangeRates=new ExchangeRates();
        exchangeRates.setEuro(BigDecimal.valueOf(currencyClient.getCurrencyRates().getUsd().getEur()));
        exchangeRates.setBritishPound(BigDecimal.valueOf(currencyClient.getCurrencyRates().getUsd().getGbp()));
        exchangeRates.setCanadianDollar(BigDecimal.valueOf(currencyClient.getCurrencyRates().getUsd().getCad()));
        exchangeRates.setJapaneseYen(BigDecimal.valueOf(currencyClient.getCurrencyRates().getUsd().getJpy()));
        exchangeRates.setIndianRupee(BigDecimal.valueOf(currencyClient.getCurrencyRates().getUsd().getInr()));
        return exchangeRates;

    }


}

