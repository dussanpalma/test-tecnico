package com.farmatodo.service;


import com.farmatodo.entity.CreditCard;

public interface CreditCardService {
    CreditCard tokenizeCard(CreditCard creditCard);
    CreditCard getByToken(String token);
}
