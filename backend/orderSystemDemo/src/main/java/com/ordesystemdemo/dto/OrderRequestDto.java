package com.ordesystemdemo.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
public class OrderRequestDto {

    @NotBlank(message = "顧客姓名不得為空")
    private String customerName;

    @NotBlank(message = "會員ID不得為空")
    private String memberId;

    @NotNull(message = "訂單總金額不得為空")
    @DecimalMin(value = "0.0", inclusive = false, message = "訂單總金額必須大於0")
    private BigDecimal totalPrice;

    @NotNull(message = "付款狀態不得為空")
    @Min(value = 0, message = "付款狀態格式不正確")
    @Max(value = 1, message = "付款狀態格式不正確")
    private Integer payStatus;

    @NotEmpty(message = "訂單中至少需要一項商品")
    @Valid
    private List<OrderProductDto> items;

}
