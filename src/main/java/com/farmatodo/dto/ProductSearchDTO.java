package com.farmatodo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductSearchDTO {

    @NotBlank(message = "El término de búsqueda es obligatorio")
    private String query;
}
