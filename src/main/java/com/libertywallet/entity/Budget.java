package com.libertywallet.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name ="budgets")
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column
    private BigDecimal current_balance;

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
