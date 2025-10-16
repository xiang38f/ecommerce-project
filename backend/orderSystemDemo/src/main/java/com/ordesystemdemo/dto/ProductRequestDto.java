package com.ordesystemdemo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    @NotBlank(message = "商品名稱不得為空")
    @Size(max = 100, message = "商品名稱長度不能超過100個字元")
    private String productName;

    @NotNull(message = "價格不得為空")
    @DecimalMin(value = "0.0", inclusive = false, message = "價格必須大於0")
    private BigDecimal price;

    @NotNull(message = "庫存數量不得為空")
    @Min(value = 0, message = "庫存數量不能為負數")
    private Integer quantity;

}
