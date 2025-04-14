package com.libertywallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_feedbacks", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "recommendation_id"}))
public class UserFeedback {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recommendation_id",nullable = false)
    private Recommendation recommendation;

    @Column(nullable = false)
    private boolean liked;
    public boolean getLiked(){
        return liked;
    }

    @Column(nullable = false)
    private boolean favorite;
    public boolean getFavorite(){
        return favorite;
    }



}
