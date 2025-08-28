package com.farmatodo.controller;

import com.farmatodo.dto.OrderDTO;
import com.farmatodo.dto.OrderItemResponseDTO;
import com.farmatodo.dto.OrderResponseDTO;
import com.farmatodo.entity.Order;
import com.farmatodo.entity.OrderItem;
import com.farmatodo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderDTO dto) {
        OrderResponseDTO orderResponse = orderService.createOrder(dto.getCustomerId(), dto.getProductIds());
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<OrderResponseDTO> payOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        orderService.processPayment(order);
        return ResponseEntity.ok(toOrderResponseDTO(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setCustomerName(order.getCustomer().getName());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> items = order.getItems().stream().map(item -> {
            OrderItemResponseDTO dto = new OrderItemResponseDTO();
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            return dto;
        }).toList();

        response.setItems(items);

        return ResponseEntity.ok(response);
    }


    private OrderResponseDTO toOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> items = order.getItems().stream().map(this::toOrderItemResponseDTO).collect(Collectors.toList());
        dto.setItems(items);

        return dto;
    }

    private OrderItemResponseDTO toOrderItemResponseDTO(OrderItem item) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }


}
