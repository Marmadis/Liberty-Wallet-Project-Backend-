package com.libertywallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Payment> paymentList;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Prediction> predictionList;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Transaction> transactionList;
}


