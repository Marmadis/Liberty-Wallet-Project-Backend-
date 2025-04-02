package com.libertywallet.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Prediction> predictionList;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Transaction> transactionList;
}


