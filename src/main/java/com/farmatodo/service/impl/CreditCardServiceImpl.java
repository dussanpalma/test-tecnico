package com.farmatodo.service.impl;

import com.farmatodo.entity.CreditCard;
import com.farmatodo.repository.CreditCardRepository;
import com.farmatodo.service.CreditCardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;

    @Value("${app.tokenization.reject-probability:0.2}")
    private double rejectProbability;

    @Value("${app.encryption.key:1234567890123456}")
    private String encryptionKey;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCard tokenizeCard(CreditCard creditCard) {
        double random = ThreadLocalRandom.current().nextDouble();
        if (random < rejectProbability) {
            throw new IllegalArgumentException("Tokenization rejected");
        }
        try {
            String encryptedNumber = encrypt(creditCard.getCardNumber());
            creditCard.setCardNumber(encryptedNumber);
            creditCard.setToken(UUID.randomUUID().toString());
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting card", e);
        }
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard getByToken(String token) {
        CreditCard card = creditCardRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        try {
            card.setCardNumber(decrypt(card.getCardNumber()));
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting card", e);
        }
        return card;
    }

    private String encrypt(String plainText) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
    }

    private String decrypt(String cipherText) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(decoded), StandardCharsets.UTF_8);
    }
}
