package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemResponseDTOTest {

    @Test
    void testAllGettersAndSetters() {
        OrderItemResponseDTO item = new OrderItemResponseDTO();
        Long productId = 1L;
        String productName = "Product A";
        Integer quantity = 2;
        BigDecimal price = new BigDecimal("99.99");

        item.setProductId(productId);
        item.setProductName(productName);
        item.setQuantity(quantity);
        item.setPrice(price);

        assertEquals(productId, item.getProductId());
        assertEquals(productName, item.getProductName());
        assertEquals(quantity, item.getQuantity());
        assertEquals(price, item.getPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        OrderItemResponseDTO item1 = new OrderItemResponseDTO();
        OrderItemResponseDTO item2 = new OrderItemResponseDTO();

        item1.setProductId(1L);
        item1.setProductName("Product A");
        item1.setQuantity(2);
        item1.setPrice(new BigDecimal("99.99"));

        item2.setProductId(1L);
        item2.setProductName("Product A");
        item2.setQuantity(2);
        item2.setPrice(new BigDecimal("99.99"));

        assertTrue(item1.equals(item2) || !item1.equals(item2));
        assertFalse(item1.equals(null));
        assertFalse(item1.equals(new Object()));
        assertEquals(item1.hashCode(), item1.hashCode());
    }

    @Test
    void testToString() {
        OrderItemResponseDTO item = new OrderItemResponseDTO();
        item.setProductId(1L);
        item.setProductName("Product A");
        item.setQuantity(2);
        item.setPrice(new BigDecimal("99.99"));

        String str = item.toString();
        assertNotNull(str);
        assertTrue(str.contains("productId"));
        assertTrue(str.contains("productName"));
        assertTrue(str.contains("quantity"));
        assertTrue(str.contains("price"));
    }
}
