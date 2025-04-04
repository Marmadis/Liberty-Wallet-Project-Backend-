package com.libertywallet.repositories;


import com.libertywallet.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUserId(Long userId);

    @Query(value = """
        SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(amount) AS total
        FROM transactions
        WHERE user_id = :userId
        GROUP BY month
        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getRawMonthlyExpenses(@Param("userId") Long userId);


}
