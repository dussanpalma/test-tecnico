package com.farmatodo.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Table(name = "credit_cards")
@Data
public class CreditCard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String cardNumber;


    @Column(nullable = false)
    private String cvv;


    @Column(nullable = false)
    private String expirationDate;


    @Column(nullable = false, unique = true)
    private String token;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

}
