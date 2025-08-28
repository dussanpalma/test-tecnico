package com.farmatodo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartCheckoutDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private String address;

    @NotNull
    private String creditCardToken;

    @NotNull
    private List<CartItemDTO> items;
}
