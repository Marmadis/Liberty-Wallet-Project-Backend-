package com.libertywallet.repository;


import com.libertywallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    @Query(value = """
        SELECT TO_CHAR(date, 'YYYY-MM') AS month, SUM(amount) AS total
        FROM transactions
        WHERE user_id = :userId
        GROUP BY month
        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getRawMonthlyExpenses(@Param("userId") Long userId);


}
