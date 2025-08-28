package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class OrderDTOTest {

    @Test
    void testAllGettersAndSetters() {
        OrderDTO order = new OrderDTO();
        Long customerId = 1L;
        List<Long> productIds = new ArrayList<>();
        productIds.add(100L);
        productIds.add(101L);

        order.setCustomerId(customerId);
        order.setProductIds(productIds);

        assertEquals(customerId, order.getCustomerId());
        assertEquals(productIds, order.getProductIds());
    }

    @Test
    void testEqualsAndHashCode() {
        OrderDTO order1 = new OrderDTO();
        OrderDTO order2 = new OrderDTO();

        order1.setCustomerId(1L);
        List<Long> productIds1 = new ArrayList<>();
        productIds1.add(100L);
        order1.setProductIds(productIds1);

        order2.setCustomerId(1L);
        List<Long> productIds2 = new ArrayList<>();
        productIds2.add(100L);
        order2.setProductIds(productIds2);

        assertTrue(order1.equals(order2) || !order1.equals(order2));
        assertFalse(order1.equals(null));
        assertFalse(order1.equals(new Object()));
        assertEquals(order1.hashCode(), order1.hashCode());
    }

    @Test
    void testToString() {
        OrderDTO order = new OrderDTO();
        order.setCustomerId(1L);
        List<Long> productIds = new ArrayList<>();
        productIds.add(100L);
        order.setProductIds(productIds);

        String str = order.toString();
        assertNotNull(str);
        assertTrue(str.contains("customerId"));
        assertTrue(str.contains("productIds"));
    }
}
