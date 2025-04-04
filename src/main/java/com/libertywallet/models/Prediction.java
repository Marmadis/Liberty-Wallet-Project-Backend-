package com.libertywallet.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="predictions")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
