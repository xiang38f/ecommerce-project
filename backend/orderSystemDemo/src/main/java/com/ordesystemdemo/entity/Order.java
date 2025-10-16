package com.ordesystemdemo.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "`Orders`")
@Getter
public class Order {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "member_id", nullable = false, length = 20)
    private String memberId;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "pay_status", nullable = false)
    private Integer payStatus;
}
