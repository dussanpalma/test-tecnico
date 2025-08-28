package com.farmatodo.controller;

import com.farmatodo.dto.CartCheckoutDTO;
import com.farmatodo.dto.CartItemDTO;
import com.farmatodo.dto.OrderItemResponseDTO;
import com.farmatodo.dto.OrderResponseDTO;
import com.farmatodo.entity.Customer;
import com.farmatodo.entity.Order;
import com.farmatodo.entity.OrderItem;
import com.farmatodo.entity.Product;
import com.farmatodo.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartControllerTest {

    private CartService cartService;
    private CartController cartController;

    @BeforeEach
    void setUp() {
        cartService = mock(CartService.class);
        cartController = new CartController(cartService);
    }

    @Test
    void testAddItem() {
        CartItemDTO item = new CartItemDTO();
        item.setProductId(1L);
        item.setQuantity(2);

        ResponseEntity<String> response = cartController.addItem(10L, item);

        ArgumentCaptor<CartItemDTO> captor = ArgumentCaptor.forClass(CartItemDTO.class);
        verify(cartService).addItem(eq(10L), captor.capture());
        assertEquals(item, captor.getValue());

        assertEquals("Producto agregado al carrito", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testRemoveItem() {
        ResponseEntity<String> response = cartController.removeItem(10L, 1L);

        verify(cartService).removeItem(10L, 1L);
        assertEquals("Producto eliminado del carrito", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCheckout() {
        CartCheckoutDTO checkoutDTO = new CartCheckoutDTO();
        checkoutDTO.setCustomerId(10L);
        checkoutDTO.setAddress("123 Street");
        checkoutDTO.setCreditCardToken("tok_123");
        CartItemDTO cartItem = new CartItemDTO();
        cartItem.setProductId(1L);
        cartItem.setQuantity(2);
        checkoutDTO.setItems(List.of(cartItem));

        Customer customer = new Customer();
        customer.setId(10L);
        customer.setName("John Doe");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product A");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("99.99"));

        Order order = new Order();
        order.setId(100L);
        order.setCustomer(customer);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(List.of(orderItem));

        when(cartService.checkout(checkoutDTO)).thenReturn(order);

        ResponseEntity<OrderResponseDTO> response = cartController.checkout(checkoutDTO);

        verify(cartService).checkout(checkoutDTO);

        OrderResponseDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(order.getId(), body.getId());
        assertEquals(order.getCustomer().getId(), body.getCustomerId());
        assertEquals(order.getCustomer().getName(), body.getCustomerName());
        assertEquals(order.getStatus(), body.getStatus());
        assertEquals(order.getCreatedAt(), body.getCreatedAt());

        assertEquals(1, body.getItems().size());
        OrderItemResponseDTO itemDTO = body.getItems().get(0);
        assertEquals(product.getId(), itemDTO.getProductId());
        assertEquals(product.getName(), itemDTO.getProductName());
        assertEquals(orderItem.getQuantity(), itemDTO.getQuantity());
        assertEquals(orderItem.getPrice(), itemDTO.getPrice());
    }
}
