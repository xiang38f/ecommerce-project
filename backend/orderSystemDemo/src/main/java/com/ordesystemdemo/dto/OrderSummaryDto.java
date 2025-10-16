package com.ordesystemdemo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSummaryDto {
    private String orderId;
    private String memberId;
    private BigDecimal totalPrice;
    private Integer payStatus;
}
