package com.farmatodo.controller;

import com.farmatodo.dto.OrderDTO;
import com.farmatodo.dto.OrderItemResponseDTO;
import com.farmatodo.dto.OrderResponseDTO;
import com.farmatodo.entity.Customer;
import com.farmatodo.entity.Order;
import com.farmatodo.entity.OrderItem;
import com.farmatodo.entity.Product;
import com.farmatodo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private OrderService orderService;
    private OrderController controller;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        controller = new OrderController(orderService);
    }

    @Test
    void testCreateOrder() {
        OrderDTO dto = new OrderDTO();
        dto.setCustomerId(1L);
        dto.setProductIds(List.of(100L, 101L));

        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setId(1L);

        when(orderService.createOrder(dto.getCustomerId(), dto.getProductIds())).thenReturn(responseDTO);

        ResponseEntity<OrderResponseDTO> response = controller.createOrder(dto);

        verify(orderService).createOrder(dto.getCustomerId(), dto.getProductIds());
        assertNotNull(response);
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testPayOrder() {
        Long orderId = 1L;

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        Product product = new Product();
        product.setId(100L);
        product.setName("Product A");

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);
        item.setPrice(new BigDecimal("99.99"));

        Order order = new Order();
        order.setId(orderId);
        order.setCustomer(customer);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(List.of(item));

        when(orderService.getOrderById(orderId)).thenReturn(order);

        ResponseEntity<OrderResponseDTO> response = controller.payOrder(orderId);

        verify(orderService).getOrderById(orderId);
        verify(orderService).processPayment(order);

        OrderResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(order.getId(), body.getId());
        assertEquals(order.getCustomer().getId(), body.getCustomerId());
        assertEquals(order.getCustomer().getName(), body.getCustomerName());
        assertEquals(order.getStatus(), body.getStatus());
        assertEquals(order.getItems().size(), body.getItems().size());
    }

    @Test
    void testGetOrder() {
        Long orderId = 1L;

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        Product product = new Product();
        product.setId(100L);
        product.setName("Product A");

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);
        item.setPrice(new BigDecimal("99.99"));

        Order order = new Order();
        order.setId(orderId);
        order.setCustomer(customer);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(List.of(item));

        when(orderService.getOrderById(orderId)).thenReturn(order);

        ResponseEntity<OrderResponseDTO> response = controller.getOrder(orderId);

        verify(orderService).getOrderById(orderId);

        OrderResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(order.getId(), body.getId());
        assertEquals(order.getCustomer().getId(), body.getCustomerId());
        assertEquals(order.getCustomer().getName(), body.getCustomerName());
        assertEquals(order.getStatus(), body.getStatus());
        assertEquals(order.getItems().size(), body.getItems().size());

        OrderItemResponseDTO itemDTO = body.getItems().get(0);
        assertEquals(product.getId(), itemDTO.getProductId());
        assertEquals(product.getName(), itemDTO.getProductName());
        assertEquals(item.getQuantity(), itemDTO.getQuantity());
        assertEquals(item.getPrice(), itemDTO.getPrice());
    }
}
