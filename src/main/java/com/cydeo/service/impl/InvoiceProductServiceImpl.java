package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Product;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.exception.InvoiceNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceService invoiceService;
    private final CompanyService companyService;
    private final MapperUtil mapperUtil;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, @Lazy InvoiceService invoiceService, CompanyService companyService, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.invoiceService = invoiceService;
        this.companyService = companyService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<InvoiceProductDTO> listAllInvoiceProduct(Long id) {
        return invoiceProductRepository.findAllByInvoiceId(id).stream()
                .map(invoiceProduct -> calculateTotalInvoiceProduct(invoiceProduct.getId()))
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());
    }
    private InvoiceProductDTO calculateTotalInvoiceProduct(Long invoiceProductId){
        InvoiceProductDTO invoiceProductDTO=findById(invoiceProductId);
        BigDecimal total=BigDecimal.ZERO;
        if (invoiceProductDTO.getQuantity() == null || invoiceProductDTO.getPrice() == null || invoiceProductDTO.getTax()==null) {
            throw new InvoiceNotFoundException("Quantity or price is null");
        }
        List<InvoiceProduct> list =invoiceProductRepository.findAllByIdAndIsDeleted(invoiceProductDTO.getId(),false);
        for (InvoiceProduct each : list) {
            total=total.add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())));//15
            total=total.add(total.multiply(BigDecimal.valueOf(each.getTax()).divide(BigDecimal.valueOf(100))));
        }
        invoiceProductDTO.setTotal(total);

        return invoiceProductDTO;
    }

    @Override
    public InvoiceProductDTO save(InvoiceProductDTO invoiceProductDTO, Long id) {
        CompanyDTO companyDTO=companyService.getCompanyDTOByLoggedInUser();
        InvoiceDTO invoice = invoiceService.findById(id);
        invoice.setCompany(companyDTO);
        invoiceProductDTO.setInvoice(invoice);
        InvoiceProduct invoiceProduct = invoiceProductRepository
                .save(mapperUtil.convert(invoiceProductDTO, new InvoiceProduct()));
        return mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());
    }

    @Override
    public InvoiceProductDTO findById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDTO());
    }

    @Override
    public InvoiceProductDTO findByInvoiceId(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findByInvoice_Id(id), new InvoiceProductDTO());
    }

    @Override
    public InvoiceProductDTO delete(Long invoiceProductId) {
        Optional<InvoiceProduct> invoiceProduct=invoiceProductRepository.findById(invoiceProductId);
        invoiceProduct.orElseThrow(()-> new InvoiceNotFoundException("Invoice can not found")).setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct.orElseThrow());
        InvoiceProductDTO invoiceProductDTO=mapperUtil.convert(invoiceProduct,new InvoiceProductDTO());
        return invoiceProductDTO;
    }
    @Override
    public BigDecimal sumOfTotalProfitLoss() {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository
                .findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompanyTitle(InvoiceStatus.APPROVED,
                        InvoiceType.SALES, companyService.getCompanyDTOByLoggedInUser().getTitle());
        BigDecimal sumOfTotalProfitLoss = BigDecimal.ZERO;
        for (InvoiceProduct invoiceProduct : invoiceProducts) {
            sumOfTotalProfitLoss = sumOfTotalProfitLoss.add(invoiceProduct.getProfitLoss());
        }

        return sumOfTotalProfitLoss;

    }

    public void setProfitLossForInvoiceProduct(InvoiceProductDTO toBeSoldProduct) {
        // Get all aproved purchased Invoice_Products by company and product id  which remaining quantity is not ZERO...
        List<InvoiceProduct> listOfApprovedPurchasedProducts = invoiceProductRepository
                .findAllByInvoiceProductsCompanyProductQuantityGreaterThanZero
                        (InvoiceStatus.APPROVED, InvoiceType.PURCHASE, toBeSoldProduct.getInvoice().getCompany().getTitle()
                                , toBeSoldProduct.getProduct().getId());

        listOfApprovedPurchasedProducts.sort(Comparator.comparing(p -> p.getInvoice().getDate()));//asc orderby date

        BigDecimal profitLoss = BigDecimal.ZERO;
        toBeSoldProduct.setProfitLoss(profitLoss);
        toBeSoldProduct.setRemainingQuantity(toBeSoldProduct.getQuantity());
        for (InvoiceProduct purchasedProduct : listOfApprovedPurchasedProducts) {

            //calculate tax
            BigDecimal soldProductTax = toBeSoldProduct.getPrice()
                    .multiply(BigDecimal.valueOf(toBeSoldProduct.getTax())).divide(BigDecimal.valueOf(100));
            BigDecimal purchasedProductTax = purchasedProduct.getPrice()
                    .multiply(BigDecimal.valueOf(purchasedProduct.getTax())).divide(BigDecimal.valueOf(100));
            if (purchasedProduct.getRemainingQuantity() >= toBeSoldProduct.getRemainingQuantity()) {
                //calculate how much money we spent to buy/purchase And how much money we earned from this sale
                //calculate profit loss
                profitLoss = (toBeSoldProduct.getPrice().add(soldProductTax).subtract(purchasedProduct.getPrice()
                        .add(purchasedProductTax))).multiply(BigDecimal.valueOf(toBeSoldProduct.getRemainingQuantity()));
                // Add new profit/loss
                BigDecimal updatedProfitLoss = toBeSoldProduct.getProfitLoss().add(profitLoss);
                //set
                toBeSoldProduct.setProfitLoss(updatedProfitLoss);
                // Set the remaining quantity
                purchasedProduct.setRemainingQuantity(purchasedProduct.getRemainingQuantity() - toBeSoldProduct.getRemainingQuantity());
                toBeSoldProduct.setRemainingQuantity(0);
                // Save (update) the purchaseInvoiceProduct and salesInvoiceProduct to the database
                invoiceProductRepository.save(purchasedProduct);
                invoiceProductRepository.save(mapperUtil.convert(toBeSoldProduct, new InvoiceProduct()));
                break;
            } else {
                profitLoss = (toBeSoldProduct.getPrice().add(soldProductTax).subtract(purchasedProduct.getPrice()
                        .add(purchasedProductTax))).multiply(BigDecimal.valueOf(purchasedProduct.getRemainingQuantity()));
                BigDecimal updatedProfitLoss = toBeSoldProduct.getProfitLoss().add(profitLoss);
                toBeSoldProduct.setProfitLoss(updatedProfitLoss);

                toBeSoldProduct.setRemainingQuantity(toBeSoldProduct.getRemainingQuantity() - purchasedProduct.getRemainingQuantity());
                purchasedProduct.setRemainingQuantity(0);
                // Save (update) the purchaseInvoiceProduct and salesInvoiceProduct to the database
                invoiceProductRepository.save(purchasedProduct);
                invoiceProductRepository.save(mapperUtil.convert(toBeSoldProduct, new InvoiceProduct()));
            }


        }
    }
}
//   .findAllByInvoiceInvoiceStatusAndInvoiceInvoiceTypeAndInvoiceCompanyTitleAndProductIdAndRemainingQuantityGreaterThanEqual
//                        (InvoiceStatus.APPROVED, InvoiceType.PURCHASE, toBeSoldProduct.getInvoice().getCompany().getTitle()
//                                , toBeSoldProduct.getProduct().getId(),1);
// .findAllByProductIdAndInvoiceCompanyTitleAndInvoiceInvoiceStatusAndInvoiceInvoiceType
//                        (toBeSoldProduct.getProduct().getId(), toBeSoldProduct.getInvoice().getCompany().getTitle()
//                                ,InvoiceStatus.APPROVED,InvoiceType.PURCHASE);
// .findAllByInvoiceProductsCompanyProductQuantityGreaterThanZero
//                        (InvoiceStatus.APPROVED, InvoiceType.PURCHASE, toBeSoldProduct.getInvoice().getCompany().getTitle()
//                                , toBeSoldProduct.getProduct().getId());