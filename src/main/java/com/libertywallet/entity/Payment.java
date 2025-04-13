package com.libertywallet.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int monthSum;

    @Column(nullable = false)
    private int numberOfMonths;

    @Column(nullable = false)
    private int currentNumberOfMonths;

    @Column(nullable = false)
    private int currentSum;

    @Column(nullable = false)
    private int generalSum;

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
