package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.dto.ProductDTO;
import com.cydeo.entity.Product;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.mapper.ProductMapper;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> listAllProducts() {

        List<Product> productsList = productRepository.findAll();

        return productsList.stream().map(

                product ->
                        mapperUtil.convert(product, new ProductDTO())).collect(Collectors.toList()
        );
    }

    @Override
    public void save(ProductDTO product) {

        Product convertedProduct = mapperUtil.convert(product, new Product());

        productRepository.save(convertedProduct);
    }

    @Override
    public ProductDTO findById(Long id) {

        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return productMapper.convertToDto(product.get());
        }

        return null;
    }

    @Override
    public void delete(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            product.get().setIsDeleted(true);
            productRepository.save(product.get());
        }

    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Product convertedProduct = mapperUtil.convert(dto, new Product());
        convertedProduct.setId(product.getId());
        productRepository.save(convertedProduct);
        return mapperUtil.convert(convertedProduct,new ProductDTO());
    }

    @Override
    public boolean checkInventory(InvoiceProductDTO invoiceProductDTO) {
        if (invoiceProductDTO.getProduct() == null) {
            return false;
        }
        Product product = productRepository.findByName(invoiceProductDTO.getProduct().getName());
        return product.getQuantityInStock() < invoiceProductDTO.getQuantity();
    }

    @Override
    public ProductDTO increaseProductInventory(Long id, Integer amount) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setQuantityInStock(product.getQuantityInStock() + amount);
        return productMapper.convertToDto(product);
    }

    @Override
    public ProductDTO decreaseProductInventory(Long id, Integer amount) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setQuantityInStock(product.getQuantityInStock() - amount);
        return productMapper.convertToDto(product);
    }


}
