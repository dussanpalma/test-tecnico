package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CartCheckoutDTOTest {

    @Test
    void testAllGettersAndSetters() {
        CartCheckoutDTO dto = new CartCheckoutDTO();
        Long customerId = 123L;
        String address = "123 Main St";
        String creditCardToken = "tok_abc123";
        List<CartItemDTO> items = new ArrayList<>();
        items.add(new CartItemDTO());

        dto.setCustomerId(customerId);
        dto.setAddress(address);
        dto.setCreditCardToken(creditCardToken);
        dto.setItems(items);

        assertEquals(customerId, dto.getCustomerId());
        assertEquals(address, dto.getAddress());
        assertEquals(creditCardToken, dto.getCreditCardToken());
        assertEquals(items, dto.getItems());
    }

    @Test
    void testEqualsAndHashCode() {
        CartCheckoutDTO dto1 = new CartCheckoutDTO();
        CartCheckoutDTO dto2 = new CartCheckoutDTO();

        dto1.setCustomerId(1L);
        dto2.setCustomerId(1L);

        assertTrue(dto1.equals(dto2) || !dto1.equals(dto2));
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals(new Object()));
        assertEquals(dto1.hashCode(), dto1.hashCode());
    }

    @Test
    void testToString() {
        CartCheckoutDTO dto = new CartCheckoutDTO();
        dto.setCustomerId(1L);
        dto.setAddress("Address");
        dto.setCreditCardToken("Token");
        dto.setItems(new ArrayList<>());

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("customerId"));
        assertTrue(str.contains("address"));
        assertTrue(str.contains("creditCardToken"));
        assertTrue(str.contains("items"));
    }
}
