package com.farmatodo.repository;

import com.farmatodo.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findByToken(String token);
}
