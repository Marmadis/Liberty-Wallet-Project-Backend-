package com.libertywallet.repository;

import com.libertywallet.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {
    Optional<Recommendation> findById(Long id);
    Optional<Recommendation> findByUserId(Long userId);

    @Query("SELECT r FROM Recommendation r " +
            "ORDER BY (SELECT COUNT(f) FROM UserFeedback f WHERE f.recommendation = r AND f.liked = true) DESC")
    List<Recommendation> findTopRecommendationsWithLikes(Pageable pageable);


    @Query("SELECT r FROM Recommendation r " +
            "WHERE r.category IN (SELECT DISTINCT uf.recommendation.category FROM UserFeedback uf " +
            "WHERE uf.user.id = :userId AND uf.liked = true) " +
            "ORDER BY r.createdAt DESC")
    List<Recommendation> findRecommendationsByUserPreferences(Long userId);
}
