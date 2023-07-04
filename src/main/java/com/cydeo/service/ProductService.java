package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.dto.ProductDTO;

import java.util.List;

public interface ProductService  {

    public List<ProductDTO> listAllProducts();
    public void save(ProductDTO product);
    public ProductDTO findById(Long id);
    public void delete(Long id);
    public ProductDTO update(Long id, ProductDTO dto);

    boolean checkQuantity(InvoiceProductDTO invoiceProductDTO);

}
