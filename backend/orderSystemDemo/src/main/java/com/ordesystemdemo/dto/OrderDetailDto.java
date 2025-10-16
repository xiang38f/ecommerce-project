package com.ordesystemdemo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailDto {
    private String orderId;
    private String memberId;
    private BigDecimal totalPrice;
    private Integer payStatus;
    private LocalDateTime orderDate;
    private List<OrderItemDto> items;
}
