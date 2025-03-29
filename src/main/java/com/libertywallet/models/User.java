package com.libertywallet.models;


import jakarta.persistence.*;
import lombok.*;


import java.util.List;
import java.time.LocalDate;

// Починить Setter/Getter.По какой-то причине Lombok не работает корректно

@Data
@Entity
@Setter
@Getter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,updatable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<ChatGptApiUsage> chatGptApiUsageList;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Budget> budgetList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactionList;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    private List<Prediction> predictionList;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }




}

