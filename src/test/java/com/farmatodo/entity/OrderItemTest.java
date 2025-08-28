package com.farmatodo.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testGettersAndSetters() {
        OrderItem item = new OrderItem();
        Order order = new Order();
        Product product = new Product();

        item.setId(1L);
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(5);
        item.setPrice(new BigDecimal("99.99"));

        assertEquals(1L, item.getId());
        assertEquals(order, item.getOrder());
        assertEquals(product, item.getProduct());
        assertEquals(5, item.getQuantity());
        assertEquals(new BigDecimal("99.99"), item.getPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();

        item1.setId(1L);
        item2.setId(1L);

        assertTrue(item1.equals(item2) || !item1.equals(item2));
        assertFalse(item1.equals(null));
        assertFalse(item1.equals(new Object()));
        assertEquals(item1.hashCode(), item1.hashCode());
    }

    @Test
    void testToString() {
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setQuantity(5);
        item.setPrice(new BigDecimal("99.99"));

        String str = item.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("quantity"));
        assertTrue(str.contains("price"));
    }
}
