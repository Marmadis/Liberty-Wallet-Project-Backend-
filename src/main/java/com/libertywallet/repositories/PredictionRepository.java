package com.libertywallet.repositories;


import com.libertywallet.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction,Long> {

    @Query(value = """
    SELECT DATE_TRUNC('month', t.date) AS month, SUM(t.amount) AS total
    FROM transactions t
    WHERE t.user_id = :userId
    GROUP BY DATE_TRUNC('month', t.date)
    ORDER BY month
""", nativeQuery = true)
    List<Object[]> getMonthlyExpenses(Long userId);

}
