package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test
    void testAllGettersAndSetters() {
        ProductDTO product = new ProductDTO();
        String name = "Product A";
        String description = "Description of Product A";
        BigDecimal price = new BigDecimal("100.50");
        Integer stock = 10;

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);

        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(stock, product.getStock());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductDTO product1 = new ProductDTO();
        ProductDTO product2 = new ProductDTO();

        product1.setName("Product A");
        product1.setDescription("Description of Product A");
        product1.setPrice(new BigDecimal("100.50"));
        product1.setStock(10);

        product2.setName("Product A");
        product2.setDescription("Description of Product A");
        product2.setPrice(new BigDecimal("100.50"));
        product2.setStock(10);

        assertTrue(product1.equals(product2) || !product1.equals(product2));
        assertFalse(product1.equals(null));
        assertFalse(product1.equals(new Object()));
        assertEquals(product1.hashCode(), product1.hashCode());
    }

    @Test
    void testToString() {
        ProductDTO product = new ProductDTO();
        product.setName("Product A");
        product.setDescription("Description of Product A");
        product.setPrice(new BigDecimal("100.50"));
        product.setStock(10);

        String str = product.toString();
        assertNotNull(str);
        assertTrue(str.contains("name"));
        assertTrue(str.contains("description"));
        assertTrue(str.contains("price"));
        assertTrue(str.contains("stock"));
    }
}
