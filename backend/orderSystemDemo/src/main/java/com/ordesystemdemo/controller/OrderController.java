package com.ordesystemdemo.controller;

import com.ordesystemdemo.dto.OrderRequestDto;
import com.ordesystemdemo.dto.OrderSummaryDto;
import com.ordesystemdemo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDto request) {
        String orderId = orderService.createOrder(request);
        return ResponseEntity.ok("訂單建立成功，訂單編號：" + orderId);
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryDto>> getAllOrders() {
        List<OrderSummaryDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

}
