package com.ordesystemdemo.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "Order_Detail")
@Getter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_sn")
    private Long orderItemSn;

    @Column(name = "order_id", nullable = false, length = 30)
    private String orderId;

    @Column(name = "product_id", nullable = false, length = 20)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "stand_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal standPrice;

    @Column(name = "item_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal itemPrice;

}
