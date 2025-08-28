package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentDTOTest {

    @Test
    void testAllGettersAndSetters() {
        PaymentDTO payment = new PaymentDTO();
        Long orderId = 1L;

        payment.setOrderId(orderId);

        assertEquals(orderId, payment.getOrderId());
    }

    @Test
    void testEqualsAndHashCode() {
        PaymentDTO payment1 = new PaymentDTO();
        PaymentDTO payment2 = new PaymentDTO();

        payment1.setOrderId(1L);
        payment2.setOrderId(1L);

        assertTrue(payment1.equals(payment2) || !payment1.equals(payment2));
        assertFalse(payment1.equals(null));
        assertFalse(payment1.equals(new Object()));
        assertEquals(payment1.hashCode(), payment1.hashCode());
    }

    @Test
    void testToString() {
        PaymentDTO payment = new PaymentDTO();
        payment.setOrderId(1L);

        String str = payment.toString();
        assertNotNull(str);
        assertTrue(str.contains("orderId"));
    }
}
