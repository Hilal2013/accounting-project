package com.cydeo.service;
import com.cydeo.dto.InvoiceProductDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReportingService {

    Map<String, BigDecimal> getProfitLossByMonth();

    List<InvoiceProductDTO> getAllStockReport();
}
