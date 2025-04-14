package com.libertywallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name="predictions")
public class Prediction {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @Column(nullable = false)
    private int predictedAmount;

    @Column(nullable = false,updatable = false)
    private LocalDate predictedData;


}
