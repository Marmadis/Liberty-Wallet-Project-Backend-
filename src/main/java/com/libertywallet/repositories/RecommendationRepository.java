package com.libertywallet.repositories;

import com.libertywallet.models.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {
    Optional<Recommendation> findById(Long id);
    Optional<Recommendation> findByUserId(Long userId);
}
