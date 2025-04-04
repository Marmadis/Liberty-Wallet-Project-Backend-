package com.libertywallet.models;


import jakarta.persistence.*;
import lombok.*;


import java.util.List;
import java.time.LocalDate;

@Data
@Entity
@Table(name="users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @Lob
    @Column
    private byte[] avatar;


    @Column(nullable = false,updatable = false)
    private LocalDate createdAt;



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

