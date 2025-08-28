package com.farmatodo.service.impl;

import com.farmatodo.entity.CreditCard;
import com.farmatodo.repository.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditCardServiceImplTest {

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @Mock
    private CreditCardRepository creditCardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTokenizeCardSuccess() {
        CreditCard card = new CreditCard();
        card.setCardNumber("1234567890123456");
        card.setCvv("123");
        card.setExpirationDate("12/30");
        when(creditCardRepository.save(any(CreditCard.class))).thenAnswer(i -> i.getArgument(0));
        CreditCard tokenized = creditCardService.tokenizeCard(card);
        assertNotNull(tokenized.getToken());
    }

    @Test
    void testTokenizeCardRejected() throws Exception {
        CreditCard card = new CreditCard();
        card.setCardNumber("1234567890123456");
        card.setCvv("123");
        card.setExpirationDate("12/30");
        var field = CreditCardServiceImpl.class.getDeclaredField("rejectProbability");
        field.setAccessible(true);
        field.set(creditCardService, 1.0);
        assertThrows(IllegalArgumentException.class, () -> creditCardService.tokenizeCard(card));
    }

    @Test
    void testGetByTokenSuccess() {
        CreditCard card = new CreditCard();
        card.setToken("token123");
        when(creditCardRepository.findByToken("token123")).thenReturn(Optional.of(card));
        CreditCard found = creditCardService.getByToken("token123");
        assertEquals("token123", found.getToken());
    }

    @Test
    void testGetByTokenNotFound() {
        when(creditCardRepository.findByToken("missing")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> creditCardService.getByToken("missing"));
    }
}
