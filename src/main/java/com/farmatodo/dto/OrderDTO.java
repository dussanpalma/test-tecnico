package com.farmatodo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {

    @NotNull(message = "El id del cliente es obligatorio")
    private Long customerId;

    @Size(min = 1, message = "Debe enviar al menos un producto")
    private List<Long> productIds;
}
