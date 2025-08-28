package com.farmatodo.service.impl;

import com.farmatodo.dto.CartCheckoutDTO;
import com.farmatodo.dto.CartItemDTO;
import com.farmatodo.entity.*;
import com.farmatodo.repository.CustomerRepository;
import com.farmatodo.repository.OrderRepository;
import com.farmatodo.repository.ProductRepository;
import com.farmatodo.service.CartService;
import com.farmatodo.service.EmailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final EmailService emailService;

    private final Map<Long, List<CartItemDTO>> cartMemory = new HashMap<>();

    @Override
    public void addItem(Long customerId, CartItemDTO item) {
        cartMemory.computeIfAbsent(customerId, k -> new ArrayList<>()).add(item);
    }

    @Override
    public void removeItem(Long customerId, Long productId) {
        List<CartItemDTO> cart = cartMemory.get(customerId);
        if (cart != null) {
            cart.removeIf(i -> i.getProductId().equals(productId));
        }
    }

    @Override
    @Transactional
    public Order checkout(CartCheckoutDTO checkoutDTO) {
        Customer customer = customerRepository.findById(checkoutDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        List<CartItemDTO> items = cartMemory.get(checkoutDTO.getCustomerId());
        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItemDTO item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

            if (product.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getName());
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        order.setItems(orderItems);
        orderRepository.save(order);

        cartMemory.remove(checkoutDTO.getCustomerId());

        try {
            emailService.sendEmail(
                    customer.getEmail(),
                    "Confirmación de pedido",
                    "Su pedido #" + order.getId() + " ha sido confirmado. Total: $" + total
            );
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }

        return order;
    }

    Map<Long, List<CartItemDTO>> getCartMemory() {
        return cartMemory;
    }
}
