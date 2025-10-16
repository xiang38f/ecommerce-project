package com.ordesystemdemo.service;

import com.ordesystemdemo.dto.ProductRequestDto;
import com.ordesystemdemo.entity.Product;
import com.ordesystemdemo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getAvailableProducts() {
        return productRepository.getAvailableProducts();
    }

    @Transactional
    public void addNewProduct(ProductRequestDto productRequestDto) {
        productRepository.addNewProduct(
                productRequestDto.getProductName(),
                productRequestDto.getPrice().doubleValue(),
                productRequestDto.getQuantity()
        );
    }

}
