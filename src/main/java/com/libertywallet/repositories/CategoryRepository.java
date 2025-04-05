package com.libertywallet.repositories;

import com.libertywallet.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findById(Long id);
    List<Category> findByUserId(Long userId);
}
