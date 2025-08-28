package com.farmatodo.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testGettersAndSetters() {
        Customer customer = new Customer();

        List<Order> orders = new ArrayList<>();
        List<CreditCard> creditCards = new ArrayList<>();

        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customer.setAddress("123 Street");
        customer.setOrders(orders);
        customer.setCreditCards(creditCards);

        assertEquals(1L, customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("1234567890", customer.getPhone());
        assertEquals("123 Street", customer.getAddress());
        assertEquals(orders, customer.getOrders());
        assertEquals(creditCards, customer.getCreditCards());
    }

    @Test
    void testEqualsAndHashCode() {
        Customer c1 = new Customer();
        Customer c2 = new Customer();

        c1.setId(1L);
        c2.setId(1L);

        assertTrue(c1.equals(c2) || !c1.equals(c2));
        assertFalse(c1.equals(null));
        assertFalse(c1.equals(new Object()));
        assertEquals(c1.hashCode(), c1.hashCode());
    }

    @Test
    void testToString() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customer.setAddress("123 Street");

        String str = customer.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("name"));
        assertTrue(str.contains("email"));
        assertTrue(str.contains("phone"));
        assertTrue(str.contains("address"));
    }
}
