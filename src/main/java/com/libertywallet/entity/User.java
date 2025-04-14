package com.libertywallet.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name="users")
public class User{
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;


    @Column
    private String avatar;


    @Column(nullable = false,updatable = false)
    private LocalDate createdAt;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Payment> paymentList;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Recommendation> recommendationList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserFeedback> userFeedbackList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Budget> budgetList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactionList;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    private List<Prediction> predictionList;


    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    private List<Category> categoryList;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }




}

