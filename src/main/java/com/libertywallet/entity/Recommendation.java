package com.libertywallet.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "recommendations")
public class Recommendation {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = true)
    private User user;


    @Column
    private String image;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "recommendation",cascade = CascadeType.ALL)
    private List<UserFeedback> userFeedbackList;



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
