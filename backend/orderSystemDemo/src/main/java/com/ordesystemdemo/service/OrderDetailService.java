package com.ordesystemdemo.service;

import com.ordesystemdemo.dto.OrderDetailDto;
import com.ordesystemdemo.dto.OrderItemDto;
import com.ordesystemdemo.entity.Order;
import com.ordesystemdemo.entity.OrderDetail;
import com.ordesystemdemo.excption.ResourceNotFoundException;
import com.ordesystemdemo.repository.OrderDetailRepository;
import com.ordesystemdemo.repository.OrderRepository;
import com.ordesystemdemo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderDetailDto getOrderDetailById(String orderId) {
        // 1. 查找主訂單，如果找不到就拋出例外
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("找不到訂單，ID: " + orderId));

        // 2. 查找此訂單的所有明細
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        // 3. 將 List<OrderDetail> 轉換成 List<OrderItemDto> (包含商品名稱)
        List<OrderItemDto> itemDtos = orderDetails.stream()
                .map(this::convertToItemDto) // 呼叫我們之前寫的輔助方法
                .collect(Collectors.toList());

        // 4. 組合最終的、完整的 DTO 物件
        OrderDetailDto detailDto = new OrderDetailDto();
        detailDto.setOrderId(order.getOrderId());
        detailDto.setMemberId(order.getMemberId());
        detailDto.setTotalPrice(order.getTotalPrice());
        detailDto.setPayStatus(order.getPayStatus());
        detailDto.setItems(itemDtos); // 把品項列表放進去

        // 5. 回傳這個完整的物件
        return detailDto;
    }

    private OrderItemDto convertToItemDto(OrderDetail detail) {
        OrderItemDto dto = new OrderItemDto();
        dto.setProductId(detail.getProductId());
        dto.setQuantity(detail.getQuantity());
        dto.setStandPrice(detail.getStandPrice());
        dto.setItemPrice(detail.getItemPrice());

        // 根據 productId 查詢商品名稱
        productRepository.findById(detail.getProductId())
                .ifPresent(product -> dto.setProductName(product.getProductName()));

        return dto;
    }



}
