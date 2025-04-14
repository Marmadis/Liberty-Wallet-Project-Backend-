package com.libertywallet.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany(mappedBy = "tagSet")
    private Set<Recommendation> recommendationSet = new HashSet<>();
}
