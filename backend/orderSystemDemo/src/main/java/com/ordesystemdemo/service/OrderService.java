package com.ordesystemdemo.service;

import com.ordesystemdemo.dto.*;
import com.ordesystemdemo.entity.Order;
import com.ordesystemdemo.entity.Product;
import com.ordesystemdemo.excption.OrderValidationException;
import com.ordesystemdemo.repository.OrderDetailRepository;
import com.ordesystemdemo.repository.OrderRepository;
import com.ordesystemdemo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository ordersRepository, OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
        this.orderRepository = ordersRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

    public Order findOrderById(String orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.stream().findFirst().isPresent()) {
            return orderOptional.stream().findFirst().orElseThrow();
        } else {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
    }

    @Transactional
    public String createOrder(OrderRequestDto request) {

        validateOrderRequest(request);

        String orderId = orderRepository.createOrder(
                request.getMemberId(),
                request.getTotalPrice(),
                request.getPayStatus() != null ? request.getPayStatus() : 0
        );

        for (OrderProductDto item : request.getItems()) {
            orderDetailRepository.createOrderDetail(
                    orderId,
                    item.getProductId(),
                    item.getQuantity(),
                    item.getStandPrice(),
                    item.getItemPrice()
            );

            productRepository.updateProductStock(
                    item.getProductId(),
                    item.getQuantity()
            );
        }

        return orderId;
    }

    public List<OrderSummaryDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll(); // 假設您用 JPA，findAll() 是內建的
        // 使用 Java Stream 將 List<Order> 轉換成 List<OrderSummaryDto>
        return orders.stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    private void validateOrderRequest(OrderRequestDto request) {

        BigDecimal calculatedTotalPrice = BigDecimal.ZERO;

        for (OrderProductDto item : request.getItems()) {
            // 從資料庫查詢最新的商品資訊
            Product productFromDb = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new OrderValidationException("找不到商品，ID: " + item.getProductId()));

            if (item.getQuantity() > productFromDb.getQuantity()) {
                throw new OrderValidationException(
                        "商品 \"" + productFromDb.getProductName() + "\" 庫存不足。" +
                                " (剩餘庫存: " + productFromDb.getQuantity() + ", 您的訂購量: " + item.getQuantity() + ")"
                );
            }

            if (item.getStandPrice().compareTo(productFromDb.getPrice()) != 0) {
                throw new OrderValidationException("商品 " + item.getProductId() + " 的價格不符");
            }

            BigDecimal calculatedItemPrice = productFromDb.getPrice().multiply(new BigDecimal(item.getQuantity()));
            if (item.getItemPrice().compareTo(calculatedItemPrice) != 0) {
                throw new OrderValidationException("商品 " + item.getProductId() + " 的小計金額不符");
            }

            calculatedTotalPrice = calculatedTotalPrice.add(calculatedItemPrice);
        }

        if (request.getTotalPrice().compareTo(calculatedTotalPrice) != 0) {
            throw new OrderValidationException("訂單總金額不符");
        }
    }

    private OrderSummaryDto convertToSummaryDto(Order order) {
        OrderSummaryDto dto = new OrderSummaryDto();
        dto.setOrderId(order.getOrderId());
        dto.setMemberId(order.getMemberId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setPayStatus(order.getPayStatus());
        return dto;
    }


}
