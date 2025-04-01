package com.libertywallet.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_feedbacks", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "recommendation_id"}))
public class UserFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recommendation_id",nullable = false)
    private Recommendation recommendation;

    @Column(nullable = false)
    private boolean liked;

    @Column(nullable = false)
    private boolean favorite;



}
