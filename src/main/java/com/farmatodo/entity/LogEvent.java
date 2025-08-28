package com.farmatodo.entity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import lombok.Data;


@Entity
@Table(name = "logs")
@Data
public class LogEvent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String uuid;


    @Column(nullable = false)
    private String event;


    @Column(nullable = false)
    private LocalDateTime timestamp;
}