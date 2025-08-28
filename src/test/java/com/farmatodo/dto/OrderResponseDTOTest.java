package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class OrderResponseDTOTest {

    @Test
    void testAllGettersAndSetters() {
        OrderResponseDTO order = new OrderResponseDTO();
        Long id = 1L;
        Long customerId = 10L;
        String customerName = "John Doe";
        String status = "PENDING";
        LocalDateTime createdAt = LocalDateTime.now();
        List<OrderItemResponseDTO> items = new ArrayList<>();
        items.add(new OrderItemResponseDTO());

        order.setId(id);
        order.setCustomerId(customerId);
        order.setCustomerName(customerName);
        order.setStatus(status);
        order.setCreatedAt(createdAt);
        order.setItems(items);

        assertEquals(id, order.getId());
        assertEquals(customerId, order.getCustomerId());
        assertEquals(customerName, order.getCustomerName());
        assertEquals(status, order.getStatus());
        assertEquals(createdAt, order.getCreatedAt());
        assertEquals(items, order.getItems());
    }

    @Test
    void testEqualsHashCodeAndCanEqual() {
        OrderResponseDTO order1 = new OrderResponseDTO();
        OrderResponseDTO order2 = new OrderResponseDTO();

        order1.setId(1L);
        order1.setCustomerId(10L);
        order1.setCustomerName("John Doe");
        order1.setStatus("PENDING");
        order1.setCreatedAt(LocalDateTime.now());
        order1.setItems(new ArrayList<>());

        order2.setId(1L);
        order2.setCustomerId(10L);
        order2.setCustomerName("John Doe");
        order2.setStatus("PENDING");
        order2.setCreatedAt(order1.getCreatedAt());
        order2.setItems(new ArrayList<>());

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
        assertFalse(order1.equals(null));
        assertFalse(order1.equals(new Object()));

        OrderResponseDTO different = new OrderResponseDTO();
        different.setId(2L);
        assertNotEquals(order1, different);
    }

    @Test
    void testToString() {
        OrderResponseDTO order = new OrderResponseDTO();
        order.setId(1L);
        order.setCustomerId(10L);
        order.setCustomerName("John Doe");
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(new ArrayList<>());

        String str = order.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("customerId"));
        assertTrue(str.contains("customerName"));
        assertTrue(str.contains("status"));
        assertTrue(str.contains("createdAt"));
        assertTrue(str.contains("items"));
    }
}
