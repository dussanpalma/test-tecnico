package com.farmatodo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentDTO {

    @NotNull(message = "El id del pedido es obligatorio")
    private Long orderId;
}
