package com.libertywallet.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name ="budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;


    @Column(nullable = false)
    private int amountLimit;

    @Column(nullable = false,updatable = false)
    private LocalDate start_date;

    @Column(nullable = false)
    private LocalDate end_date;

    @PrePersist
    protected void onCreate(){
        this.start_date = LocalDate.now();
        this.end_date = LocalDate.now();
    }



}
