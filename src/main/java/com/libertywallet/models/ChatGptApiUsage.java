package com.libertywallet.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "api_usage")
public class ChatGptApiUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate timestamp;

    @OneToMany(mappedBy = "chatGptApiUsage", cascade = CascadeType.ALL)
    private List<Recommendation> recommendationList;


    @PrePersist
    protected void onCreate(){
        this.timestamp = LocalDate.now();
    }
}
