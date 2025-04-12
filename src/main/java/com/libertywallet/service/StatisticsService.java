package com.libertywallet.service;

import com.libertywallet.entity.Budget;
import com.libertywallet.entity.CategoryType;
import com.libertywallet.entity.Transaction;
import com.libertywallet.entity.User;
import com.libertywallet.repository.BudgetRepository;
import com.libertywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    public ExpenseAnalysisDTO getUserExpenseAnalysis(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        User user = optionalUser.get();
        List<Transaction> transactions = user.getTransactionList();


        Optional<Budget> optionalBudget = budgetRepository.findByUserId(user.getId());
        Budget budget = optionalBudget.get();
        double balance = budget.getCurrent_balance();

        if (transactions == null || transactions.isEmpty()) {
            return new ExpenseAnalysisDTO(0.0, 0.0, 0.0, new HashMap<>());
        }

        double totalIncome = transactions.stream()
                .filter(t -> t.getCategory().getType() == CategoryType.INCOME)
                .mapToDouble(t -> (double)t.getAmount())
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getCategory().getType() == CategoryType.EXPENSE)
                .mapToDouble(t -> (double)t.getAmount())
                .sum();


        Map<String, Double> expenseByCategory = transactions.stream()
                .filter(t -> t.getCategory().getType() == CategoryType.EXPENSE)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(t -> (double)t.getAmount())
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