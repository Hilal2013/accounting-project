package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductService {

    List<InvoiceProductDTO> listAllInvoiceProduct(Long id);
    InvoiceProductDTO save(InvoiceProductDTO invoiceProductDTO, Long id);
    InvoiceProductDTO findById(Long id);
    InvoiceProductDTO findByInvoiceId(Long id);
    InvoiceProductDTO delete(Long invoiceProductId);
    BigDecimal sumOfTotalProfitLoss();
    void setProfitLossForInvoiceProduct(InvoiceProductDTO toBeSoldProduct);

}
