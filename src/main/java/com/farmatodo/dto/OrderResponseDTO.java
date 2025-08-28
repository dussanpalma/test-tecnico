package com.farmatodo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> items;
}
