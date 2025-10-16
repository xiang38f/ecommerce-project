package com.ordesystemdemo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal standPrice;
    private BigDecimal itemPrice;
}
