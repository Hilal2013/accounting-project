package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.User;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    private final MapperUtil mapperUtil;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public InvoiceDTO findById(Long id) {

            return mapperUtil.convert(invoiceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Invoice couldn't find.")), new InvoiceDTO());
        }


    @Override
    public InvoiceDTO save(InvoiceDTO invoice) {
        Invoice invoice1 = mapperUtil.convert(invoice, new Invoice());
        invoice1.setId(invoice.getId());
        invoiceRepository.save(invoice1);
        return mapperUtil.convert(invoice1,new InvoiceDTO());


    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoice) {

        Optional<Invoice> invoice2 = invoiceRepository.findById(invoice.getId());

        Invoice updatedInvoice=mapperUtil.convert(invoice,new Invoice());
        updatedInvoice.setClientVendor(invoice2.get().getClientVendor());

        invoiceRepository.save(updatedInvoice);
        return mapperUtil.convert(updatedInvoice,new InvoiceDTO());
    }



    @Override
    public List<InvoiceDTO> listAllInvoice() {
        return invoiceRepository.findAll().stream()
                .map(invoice-> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());

    }

    @Override
    public InvoiceDTO delete(Long id) {
        Invoice invoice = invoiceRepository.findByIdAndIsDeleted(id, false);
        InvoiceDTO invoiceDTO=mapperUtil.convert(invoice,new InvoiceDTO());
        if(invoiceDTO.getInvoiceStatus().getValue().equals("Awaiting Approval")) {
            invoice.setIsDeleted(true);
            invoiceRepository.save(invoice);
        }
        return invoiceDTO;
    }

    @Override
    public InvoiceDTO approve(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoiceRepository.save(invoice);
        return mapperUtil.convert(invoice, new InvoiceDTO());
    }

    @Override
    public InvoiceDTO createNewSalesInvoice() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNo("S-00" + (invoiceRepository.findAllByInvoiceType(InvoiceType.SALES).size() + 1));
        invoiceDTO.setDate(LocalDate.now());
        return invoiceDTO;
    }

    @Override
    public InvoiceDTO createNewPurchasesInvoice() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNo("P-00" + (invoiceRepository.findAllByInvoiceType(InvoiceType.PURCHASE).size() + 1));
        invoiceDTO.setDate(LocalDate.now());
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoiceDTO.setInvoiceType(InvoiceType.PURCHASE);
        return invoiceDTO;

    }


}
