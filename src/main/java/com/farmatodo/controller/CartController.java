package com.farmatodo.controller;

import com.farmatodo.dto.CartCheckoutDTO;
import com.farmatodo.dto.CartItemDTO;
import com.farmatodo.dto.OrderItemResponseDTO;
import com.farmatodo.dto.OrderResponseDTO;
import com.farmatodo.entity.Order;
import com.farmatodo.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addItem(
            @RequestParam Long customerId,
            @Valid @RequestBody CartItemDTO item) {
        cartService.addItem(customerId, item);
        return ResponseEntity.ok("Producto agregado al carrito");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeItem(
            @RequestParam Long customerId,
            @RequestParam Long productId) {
        cartService.removeItem(customerId, productId);
        return ResponseEntity.ok("Producto eliminado del carrito");
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(@Valid @RequestBody CartCheckoutDTO checkoutDTO) {
        Order order = cartService.checkout(checkoutDTO);

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setCustomerName(order.getCustomer().getName());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(order.getItems().stream().map(item -> {
            OrderItemResponseDTO dto = new OrderItemResponseDTO();
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            return dto;
        }).toList());

        return ResponseEntity.ok(response);
    }
}
