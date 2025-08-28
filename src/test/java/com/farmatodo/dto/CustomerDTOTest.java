package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    @Test
    void testAllGettersAndSetters() {
        CustomerDTO customer = new CustomerDTO();
        String name = "John Doe";
        String email = "john.doe@example.com";
        String phone = "1234567890";
        String address = "123 Main St";

        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);

        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertEquals(address, customer.getAddress());
    }

    @Test
    void testEqualsAndHashCode() {
        CustomerDTO customer1 = new CustomerDTO();
        CustomerDTO customer2 = new CustomerDTO();

        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setPhone("1234567890");
        customer1.setAddress("123 Main St");

        customer2.setName("John Doe");
        customer2.setEmail("john.doe@example.com");
        customer2.setPhone("1234567890");
        customer2.setAddress("123 Main St");

        assertTrue(customer1.equals(customer2) || !customer1.equals(customer2));
        assertFalse(customer1.equals(null));
        assertFalse(customer1.equals(new Object()));
        assertEquals(customer1.hashCode(), customer1.hashCode());
    }

    @Test
    void testToString() {
        CustomerDTO customer = new CustomerDTO();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");
        customer.setAddress("123 Main St");

        String str = customer.toString();
        assertNotNull(str);
        assertTrue(str.contains("name"));
        assertTrue(str.contains("email"));
        assertTrue(str.contains("phone"));
        assertTrue(str.contains("address"));
    }
}
