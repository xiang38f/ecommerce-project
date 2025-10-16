package com.ordesystemdemo.controller;

import com.ordesystemdemo.dto.ProductRequestDto;
import com.ordesystemdemo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/available")
    public ResponseEntity<Object> getAvailableProducts() {
        var products = productService.getAvailableProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping()
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        productService.addNewProduct(productRequestDto);
        return ResponseEntity.ok("商品新增成功！");
    }

}
