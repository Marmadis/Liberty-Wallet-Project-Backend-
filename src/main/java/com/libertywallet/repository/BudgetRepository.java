package com.libertywallet.repository;


import com.libertywallet.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    Optional<Budget> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);

    @Query("SELECT b.amountLimit - COALESCE(SUM(t.amount), 0) FROM Budget b " +
            "LEFT JOIN Transaction t ON t.user.id = b.user.id " +
            "WHERE b.user.id = :userId AND t.date BETWEEN b.start_date AND b.end_date")
    Optional<BigDecimal> findCurrentBudgetBalance(@Param("userId") UUID userId);


}
