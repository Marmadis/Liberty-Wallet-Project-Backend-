package com.libertywallet.service;


import com.libertywallet.dto.BudgetDto;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.entity.Budget;
import com.libertywallet.entity.User;
import com.libertywallet.mapper.BudgetMapper;
import com.libertywallet.repository.BudgetRepository;
import com.libertywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor

public class BudgetService {

    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public BudgetDto getBudgetInformation(UUID userId){
        log.info("Finding user budget information...");
        Budget budgetInfo = budgetRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User  not found (user id:"+userId+")"));
        log.info("User budget information found");
        if(budgetInfo.getCurrent_balance() < budgetInfo.getAmountLimit()){
            budgetInfo.setAmountLimitWarn(true);
        }

        BudgetDto budgetDto = budgetMapper.toDto(budgetInfo);

        return budgetDto;
    }

    public String createBudgetInformation(UUID userId, BudgetDto budgetDto){
        log.info("Setting new budget information");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User  not found (user id:"+userId+")"));
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setAmountLimit(budgetDto.getAmountLimit());
        budget.setStart_date(budgetDto.getStart_date());
        budget.setEnd_date(budgetDto.getEnd_date());
        budget.setCurrent_balance(budgetDto.getCurrent_balance());
        log.info("User budget information was set");
        budgetRepository.save(budget);
        return "Budget information successfully written";
    }

    public String editBudget(UUID userId, BudgetDto budgetDto){
        Budget budget = budgetRepository.findByUserId(userId)
                .orElseThrow(()-> new NotFoundException("Budget by this userId not found"));

        budget.setCurrent_balance(budgetDto.getCurrent_balance());
        budget.setAmountLimit(budgetDto.getAmountLimit());
        budget.setStart_date(budgetDto.getStart_date());
        budget.setEnd_date(budgetDto.getEnd_date());

        budgetRepository.save(budget);
        return "Edited budget was successfully";
    }

    public String deleteBudget(UUID userId){
        budgetRepository.deleteByUserId(userId);
        return "Deleted budget was successfully";
    }

}
