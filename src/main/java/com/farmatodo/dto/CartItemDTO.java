package com.farmatodo.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class CartItemDTO {
    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
