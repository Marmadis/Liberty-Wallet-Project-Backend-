package com.libertywallet.services;

import com.libertywallet.models.CategoryType;
import com.libertywallet.models.Transaction;
import com.libertywallet.models.User;
import com.libertywallet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;

    public ExpenseAnalysisDTO getUserExpenseAnalysis(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        User user = optionalUser.get();
        List<Transaction> transactions = user.getTransactionList();

        if (transactions == null || transactions.isEmpty()) {
            return new ExpenseAnalysisDTO(0.0, 0.0, 0.0, new HashMap<>());
        }

        double totalIncome = transactions.stream()
                .filter(t -> t.getCategory().getType() == CategoryType.INCOME)
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getCategory().getType() == CategoryType.EXPENSE)
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();

        double balance = totalIncome - totalExpense;

        Map<String, Double> expenseByCategory = transactions.stream()
                .filter(t -> t.getCategory().getType() == CategoryType.EXPENSE)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(t -> t.getAmount().doubleValue())
                ));

        Map<String, Double> categoryPercents = expenseByCategory.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> totalExpense == 0 ? 0.0 : e.getValue() / totalExpense
                ));

        return new ExpenseAnalysisDTO(totalIncome, totalExpense, balance, categoryPercents);
    }

    public record ExpenseAnalysisDTO(
            double totalIncome,
            double totalExpense,
            double balance,
            Map<String, Double> expensePercents
    ) {}
}