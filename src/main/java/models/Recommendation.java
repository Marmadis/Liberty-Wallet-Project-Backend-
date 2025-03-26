package models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "recommendations")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Lob
    @Column(nullable = false)
    private String rec_text;

    @Column(nullable = false,updatable = false)
    private LocalDateTime date_generated;

    @PrePersist
    protected void onCreate() {
        this.date_generated = LocalDateTime.now();
    }
}
