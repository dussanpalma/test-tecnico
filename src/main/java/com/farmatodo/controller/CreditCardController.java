package com.farmatodo.controller;

import com.farmatodo.dto.CreditCardDTO;
import com.farmatodo.entity.CreditCard;
import com.farmatodo.entity.Customer;
import com.farmatodo.service.CreditCardService;
import com.farmatodo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cards")
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final CustomerService customerService;

    public CreditCardController(CreditCardService creditCardService, CustomerService customerService) {
        this.creditCardService = creditCardService;
        this.customerService = customerService;
    }

    @PostMapping("/tokenize")
    public ResponseEntity<CreditCard> tokenize(@Valid @RequestBody CreditCardDTO dto) {
        Customer customer = customerService.getCustomerById(dto.getCustomerId());
        CreditCard card = new CreditCard();
        card.setCardNumber(dto.getCardNumber());
        card.setCvv(dto.getCvv());
        card.setExpirationDate(dto.getExpirationDate());
        card.setCustomer(customer);

        CreditCard tokenized = creditCardService.tokenizeCard(card);
        return ResponseEntity.ok(tokenized);
    }

    @GetMapping("/{token}")
    public ResponseEntity<CreditCard> getByToken(@PathVariable String token) {
        CreditCard card = creditCardService.getByToken(token);
        return ResponseEntity.ok(card);
    }
}
