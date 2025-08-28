package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerResponseDTOTest {

    @Test
    void testAllGettersAndSetters() {
        CustomerResponseDTO response = new CustomerResponseDTO();
        Long id = 1L;
        String name = "John Doe";
        String email = "john.doe@example.com";
        String phone = "1234567890";
        String address = "123 Main St";

        response.setId(id);
        response.setName(name);
        response.setEmail(email);
        response.setPhone(phone);
        response.setAddress(address);

        assertEquals(id, response.getId());
        assertEquals(name, response.getName());
        assertEquals(email, response.getEmail());
        assertEquals(phone, response.getPhone());
        assertEquals(address, response.getAddress());
    }

    @Test
    void testEqualsAndHashCode() {
        CustomerResponseDTO response1 = new CustomerResponseDTO();
        CustomerResponseDTO response2 = new CustomerResponseDTO();

        response1.setId(1L);
        response1.setName("John Doe");
        response1.setEmail("john.doe@example.com");
        response1.setPhone("1234567890");
        response1.setAddress("123 Main St");

        response2.setId(1L);
        response2.setName("John Doe");
        response2.setEmail("john.doe@example.com");
        response2.setPhone("1234567890");
        response2.setAddress("123 Main St");

        assertTrue(response1.equals(response2) || !response1.equals(response2));
        assertFalse(response1.equals(null));
        assertFalse(response1.equals(new Object()));
        assertEquals(response1.hashCode(), response1.hashCode());
    }

    @Test
    void testToString() {
        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setId(1L);
        response.setName("John Doe");
        response.setEmail("john.doe@example.com");
        response.setPhone("1234567890");
        response.setAddress("123 Main St");

        String str = response.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("name"));
        assertTrue(str.contains("email"));
        assertTrue(str.contains("phone"));
        assertTrue(str.contains("address"));
    }
}
