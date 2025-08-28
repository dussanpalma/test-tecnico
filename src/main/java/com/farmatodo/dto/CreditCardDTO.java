package com.farmatodo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreditCardDTO {

    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "\\d{16}", message = "El número de tarjeta debe tener 16 dígitos")
    private String cardNumber;

    @NotBlank(message = "El CVV es obligatorio")
    @Pattern(regexp = "\\d{3}", message = "El CVV debe tener 3 dígitos")
    private String cvv;

    @NotBlank(message = "La fecha de expiración es obligatoria")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Formato de fecha debe ser MM/AA")
    private String expirationDate;

    private Long customerId;
}
