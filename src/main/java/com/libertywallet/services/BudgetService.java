package com.libertywallet.services;


import com.libertywallet.exception.NotFoundException;
import com.libertywallet.models.Budget;
import com.libertywallet.models.User;
import com.libertywallet.repositories.BudgetRepository;
import com.libertywallet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor

public class BudgetService {

    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;


    public Budget getBudgetInformation(Long userId){
        log.info("Finding user budget information...");
        Budget budgetInfo = budgetRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User  not found (user id:"+userId+")"));
        log.info("User budget information found");
        return budgetInfo;
    }

    public Budget createBudgetInformation(Long userId, int amountLimit, BigDecimal current_balance, LocalDate start,LocalDate end){
        log.info("Setting new budget information");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User  not found (user id:"+userId+")"));
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setAmountLimit(amountLimit);
        budget.setStart_date(start);
        budget.setEnd_date(end);
        budget.setCurrent_balance(current_balance);
        log.info("User budget information was set");
        return budgetRepository.save(budget);
    }

}
