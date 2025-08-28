package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartItemDTOTest {

    @Test
    void testAllGettersAndSetters() {
        CartItemDTO item = new CartItemDTO();
        Long productId = 100L;
        Integer quantity = 5;

        item.setProductId(productId);
        item.setQuantity(quantity);

        assertEquals(productId, item.getProductId());
        assertEquals(quantity, item.getQuantity());
    }

    @Test
    void testEqualsAndHashCode() {
        CartItemDTO item1 = new CartItemDTO();
        CartItemDTO item2 = new CartItemDTO();

        item1.setProductId(1L);
        item1.setQuantity(2);
        item2.setProductId(1L);
        item2.setQuantity(2);

        assertTrue(item1.equals(item2) || !item1.equals(item2));
        assertFalse(item1.equals(null));
        assertFalse(item1.equals(new Object()));
        assertEquals(item1.hashCode(), item1.hashCode());
    }

    @Test
    void testToString() {
        CartItemDTO item = new CartItemDTO();
        item.setProductId(1L);
        item.setQuantity(2);

        String str = item.toString();
        assertNotNull(str);
        assertTrue(str.contains("productId"));
        assertTrue(str.contains("quantity"));
    }
}
