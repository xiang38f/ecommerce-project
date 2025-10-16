package com.ordesystemdemo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDto {

    @NotBlank(message = "商品ID不得為空")
    @Pattern(regexp = "^P\\d{3,}$", message = "商品ID格式不正確，應為P開頭加上至少三位數字")
    private String productId;

    @NotNull(message = "購買數量不得為空")
    @Min(value = 1, message = "購買數量至少為1")
    private Integer quantity;

    @NotNull(message = "商品單價不得為空")
    @DecimalMin(value = "0.0", inclusive = false, message = "商品單價必須大於0")
    private BigDecimal standPrice;

    @NotNull(message = "品項總價不得為空")
    @DecimalMin(value = "0.0", inclusive = false, message = "品項總價必須大於0")
    private BigDecimal itemPrice;

}
