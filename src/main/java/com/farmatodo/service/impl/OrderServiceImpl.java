package com.farmatodo.service.impl;

import com.farmatodo.dto.OrderItemResponseDTO;
import com.farmatodo.dto.OrderResponseDTO;
import com.farmatodo.entity.Customer;
import com.farmatodo.entity.Order;
import com.farmatodo.entity.OrderItem;
import com.farmatodo.entity.Product;
import com.farmatodo.repository.CustomerRepository;
import com.farmatodo.repository.OrderRepository;
import com.farmatodo.repository.ProductRepository;
import com.farmatodo.service.EmailService;
import com.farmatodo.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;

    @Value("${app.payment.reject-probability:0.3}")
    private double rejectProbability;

    @Value("${app.payment.retry-attempts:3}")
    private int maxRetries;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository,
                            EmailService emailService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.emailService = emailService;
    }

    @Override
    public OrderResponseDTO createOrder(Long customerId, List<Long> productIds) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PENDING");

        List<OrderItem> items = productIds.stream().map(productId -> {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(1);
            item.setPrice(product.getPrice());
            return item;
        }).toList();

        order.setItems(items);
        orderRepository.save(order);

        return toResponseDTO(order);
    }


    @Override
    public void processPayment(Order order) {
        Customer customer = order.getCustomer();
        boolean paid = false;

        for (int i = 0; i < maxRetries; i++) {
            double random = ThreadLocalRandom.current().nextDouble();
            if (random >= rejectProbability) {
                order.setStatus("PAID");
                orderRepository.save(order);
                paid = true;
                break;
            }
        }

        if (!paid) {
            order.setStatus("FAILED");
            orderRepository.save(order);
        }

        try {
            if (paid) {
                emailService.sendEmail(
                        customer.getEmail(),
                        "Pago exitoso",
                        "Su pago para el pedido #" + order.getId() + " fue aprobado."
                );
            } else {
                emailService.sendEmail(
                        customer.getEmail(),
                        "Pago fallido",
                        "Su pago para el pedido #" + order.getId() + " fue rechazado después de " + maxRetries + " intentos."
                );
            }
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public OrderResponseDTO toResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getName());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        dto.setItems(order.getItems().stream().map(item -> {
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            return itemDTO;
        }).collect(Collectors.toList()));

        return dto;
    }

}
