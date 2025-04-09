package com.libertywallet.service;


import com.libertywallet.dto.BudgetDto;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.entity.Budget;
import com.libertywallet.entity.User;
import com.libertywallet.repository.BudgetRepository;
import com.libertywallet.repository.UserRepository;
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


    public BudgetDto getBudgetInformation(Long userId){
        log.info("Finding user budget information...");
        Budget budgetInfo = budgetRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User  not found (user id:"+userId+")"));
        log.info("User budget information found");
        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setCurrent_balance(budgetInfo.getCurrent_balance());
        budgetDto.setAmountLimit(budgetInfo.getAmountLimit());
        budgetDto.setStart_date(budgetInfo.getStart_date());
        budgetDto.setEnd_date(budgetInfo.getEnd_date());
        return budgetDto;
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
