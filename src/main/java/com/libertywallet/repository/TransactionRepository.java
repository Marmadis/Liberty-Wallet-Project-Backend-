package com.libertywallet.repository;


import com.libertywallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;


public interface TransactionRepository extends JpaRepository<Transaction,UUID> {

    List<Transaction> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
    @Query(value = """
        SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(amount) AS total
        FROM transactions
        WHERE user_id = :userId
        GROUP BY month
        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getRawMonthlyExpenses(@Param("userId") UUID userId);

    @Query(value = "SELECT " +
            "DATE_TRUNC('month', t.date) AS month_year, " +
            "SUM(t.amount) AS total_expenses " +
            "FROM transactions t " +
            "JOIN categories c ON t.category_id = c.id " +
            "WHERE c.type = 'EXPENSE' AND t.user_id = :userId " +
            "GROUP BY month_year " +
            "ORDER BY month_year", nativeQuery = true)
    List<Object[]> getAllMonthSumExpenses(@Param("userId") UUID userId);
}
