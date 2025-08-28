package com.farmatodo.controller;

import com.farmatodo.dto.CreditCardDTO;
import com.farmatodo.entity.CreditCard;
import com.farmatodo.entity.Customer;
import com.farmatodo.service.CreditCardService;
import com.farmatodo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditCardControllerTest {

    private CreditCardService creditCardService;
    private CustomerService customerService;
    private CreditCardController controller;

    @BeforeEach
    void setUp() {
        creditCardService = mock(CreditCardService.class);
        customerService = mock(CustomerService.class);
        controller = new CreditCardController(creditCardService, customerService);
    }

    @Test
    void testTokenize() {
        CreditCardDTO dto = new CreditCardDTO();
        dto.setCustomerId(1L);
        dto.setCardNumber("1234567812345678");
        dto.setCvv("123");
        dto.setExpirationDate("12/25");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        CreditCard card = new CreditCard();
        card.setCardNumber(dto.getCardNumber());
        card.setCvv(dto.getCvv());
        card.setExpirationDate(dto.getExpirationDate());
        card.setCustomer(customer);

        CreditCard tokenized = new CreditCard();
        tokenized.setCardNumber("****5678");
        tokenized.setCvv("123");
        tokenized.setExpirationDate("12/25");
        tokenized.setCustomer(customer);

        when(creditCardService.tokenizeCard(any(CreditCard.class))).thenReturn(tokenized);

        ResponseEntity<CreditCard> response = controller.tokenize(dto);

        verify(customerService).getCustomerById(1L);
        verify(creditCardService).tokenizeCard(any(CreditCard.class));

        assertNotNull(response);
        assertEquals(tokenized, response.getBody());
    }

    @Test
    void testGetByToken() {
        String token = "tok_123";
        CreditCard card = new CreditCard();
        card.setCardNumber("****5678");

        when(creditCardService.getByToken(token)).thenReturn(card);

        ResponseEntity<CreditCard> response = controller.getByToken(token);

        verify(creditCardService).getByToken(token);

        assertNotNull(response);
        assertEquals(card, response.getBody());
    }
}
