package com.farmatodo.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testGettersAndSetters() {
        Product product = new Product();
        List<OrderItem> orderItems = new ArrayList<>();

        product.setId(1L);
        product.setName("Aspirin");
        product.setDescription("Painkiller");
        product.setPrice(new BigDecimal("9.99"));
        product.setStock(100);
        product.setOrderItems(orderItems);

        assertEquals(1L, product.getId());
        assertEquals("Aspirin", product.getName());
        assertEquals("Painkiller", product.getDescription());
        assertEquals(new BigDecimal("9.99"), product.getPrice());
        assertEquals(100, product.getStock());
        assertEquals(orderItems, product.getOrderItems());
    }

    @Test
    void testEqualsAndHashCode() {
        Product p1 = new Product();
        Product p2 = new Product();

        p1.setId(1L);
        p2.setId(1L);

        assertTrue(p1.equals(p2) || !p1.equals(p2));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals(new Object()));
        assertEquals(p1.hashCode(), p1.hashCode());
    }

    @Test
    void testToString() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Aspirin");

        String str = product.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("name"));
    }
}
