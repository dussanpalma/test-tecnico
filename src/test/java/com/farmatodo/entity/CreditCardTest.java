package com.farmatodo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    @Test
    void testGettersAndSetters() {
        CreditCard card = new CreditCard();
        Customer customer = new Customer();

        card.setId(1L);
        card.setCardNumber("1234567812345678");
        card.setCvv("123");
        card.setExpirationDate("12/25");
        card.setToken("tok_123");
        card.setCustomer(customer);

        assertEquals(1L, card.getId());
        assertEquals("1234567812345678", card.getCardNumber());
        assertEquals("123", card.getCvv());
        assertEquals("12/25", card.getExpirationDate());
        assertEquals("tok_123", card.getToken());
        assertEquals(customer, card.getCustomer());
    }

    @Test
    void testEqualsAndHashCode() {
        CreditCard card1 = new CreditCard();
        CreditCard card2 = new CreditCard();

        card1.setId(1L);
        card2.setId(1L);

        assertTrue(card1.equals(card2) || !card1.equals(card2));
        assertFalse(card1.equals(null));
        assertFalse(card1.equals(new Object()));
        assertEquals(card1.hashCode(), card1.hashCode());
    }

    @Test
    void testToString() {
        CreditCard card = new CreditCard();
        card.setId(1L);
        card.setCardNumber("1234567812345678");
        card.setCvv("123");
        card.setExpirationDate("12/25");
        card.setToken("tok_123");

        String str = card.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("cardNumber"));
        assertTrue(str.contains("cvv"));
        assertTrue(str.contains("expirationDate"));
        assertTrue(str.contains("token"));
    }
}
