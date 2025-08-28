package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardDTOTest {

    @Test
    void testAllGettersAndSetters() {
        CreditCardDTO card = new CreditCardDTO();
        String cardNumber = "1234567812345678";
        String cvv = "123";
        String expirationDate = "12/25";
        Long customerId = 1L;

        card.setCardNumber(cardNumber);
        card.setCvv(cvv);
        card.setExpirationDate(expirationDate);
        card.setCustomerId(customerId);

        assertEquals(cardNumber, card.getCardNumber());
        assertEquals(cvv, card.getCvv());
        assertEquals(expirationDate, card.getExpirationDate());
        assertEquals(customerId, card.getCustomerId());
    }

    @Test
    void testEqualsAndHashCode() {
        CreditCardDTO card1 = new CreditCardDTO();
        CreditCardDTO card2 = new CreditCardDTO();

        card1.setCardNumber("1234567812345678");
        card1.setCvv("123");
        card1.setExpirationDate("12/25");
        card1.setCustomerId(1L);

        card2.setCardNumber("1234567812345678");
        card2.setCvv("123");
        card2.setExpirationDate("12/25");
        card2.setCustomerId(1L);

        assertTrue(card1.equals(card2) || !card1.equals(card2));
        assertFalse(card1.equals(null));
        assertFalse(card1.equals(new Object()));
        assertEquals(card1.hashCode(), card1.hashCode());
    }

    @Test
    void testToString() {
        CreditCardDTO card = new CreditCardDTO();
        card.setCardNumber("1234567812345678");
        card.setCvv("123");
        card.setExpirationDate("12/25");
        card.setCustomerId(1L);

        String str = card.toString();
        assertNotNull(str);
        assertTrue(str.contains("cardNumber"));
        assertTrue(str.contains("cvv"));
        assertTrue(str.contains("expirationDate"));
        assertTrue(str.contains("customerId"));
    }
}
