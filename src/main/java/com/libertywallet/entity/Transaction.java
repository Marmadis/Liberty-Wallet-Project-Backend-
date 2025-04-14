package com.libertywallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false )
    private Category category;
}
