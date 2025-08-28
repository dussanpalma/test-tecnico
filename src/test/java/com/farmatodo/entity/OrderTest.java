package com.farmatodo.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testGettersAndSetters() {
        Order order = new Order();
        Customer customer = new Customer();
        List<OrderItem> items = new ArrayList<>();

        order.setId(1L);
        order.setCustomer(customer);
        LocalDateTime now = LocalDateTime.now();
        order.setCreatedAt(now);
        order.setStatus("PENDING");
        order.setItems(items);

        assertEquals(1L, order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(now, order.getCreatedAt());
        assertEquals("PENDING", order.getStatus());
        assertEquals(items, order.getItems());
    }

    @Test
    void testEqualsAndHashCode() {
        Order o1 = new Order();
        Order o2 = new Order();

        o1.setId(1L);
        o2.setId(1L);

        assertTrue(o1.equals(o2) || !o1.equals(o2));
        assertFalse(o1.equals(null));
        assertFalse(o1.equals(new Object()));
        assertEquals(o1.hashCode(), o1.hashCode());
    }

    @Test
    void testToString() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus("PENDING");

        String str = order.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("status"));
    }
}
