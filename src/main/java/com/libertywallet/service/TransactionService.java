package com.libertywallet.service;


import com.libertywallet.dto.TransactionDto;
import com.libertywallet.entity.*;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.mapper.TransactionMapper;
import com.libertywallet.repository.BudgetRepository;
import com.libertywallet.repository.TransactionRepository;
import com.libertywallet.repository.UserRepository;
import com.libertywallet.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;
    private final BudgetRepository budgetRepository;

    public List<TransactionDto> getAllTransaction(Long userId){
        log.info("Getting all transaction info");
        List<Transaction> transactionList = transactionRepository.findByUserId(userId);
        List<TransactionDto> dtos = transactionList.stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());

        if(transactionList.isEmpty()){
            log.warn("Transaction list is empty");
            throw  new NotFoundException("Transaction list is empty");
        }
        log.info("Got all transaction info");
        return  dtos;
    }

    public String createTransaction(Long userId, Long categoryId, TransactionDto transactionDto){
        log.info("Creating new transaction...");
        Transaction transaction = new Transaction();
        Category category = categoryRepository.findById(categoryId)
          .orElseThrow(() ->  new NotFoundException("Category not found (user id:"+categoryId+")"));
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new NotFoundException("User not found (user id:"+userId+")"));
        transaction.setAmount(transactionDto.getAmount());
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setDescription(transactionDto.getDescription());
        transaction.setDate(transactionDto.getDate());

        if(category.getType().equals(CategoryType.INCOME)){
            Budget budget = budgetRepository.findByUserId(userId)
                    .orElseThrow(() -> new NotFoundException("User not found:"+userId));

            budget.setCurrent_balance(transactionDto.getAmount()+budget.getCurrent_balance());
            budgetRepository.save(budget);
        } else if(category.getType().equals(CategoryType.EXPENSE)){
            Budget budget = budgetRepository.findByUserId(userId)
                    .orElseThrow(() -> new NotFoundException("User not found:"+userId));
            budget.setCurrent_balance(budget.getCurrent_balance()-transactionDto.getAmount());
            budgetRepository.save(budget);
        }

        transactionRepository.save(transaction);

        log.info("Created transaction was successfully!");
        return "Created transaction was successfully!";
    }

    public String editTransaction(Long userId,Long transactionId,TransactionDto transactionDto){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found:"+transactionId));
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setDate(transactionDto.getDate());

        if(transactionDto.getAmount() != transaction.getAmount()) {
            Budget budget = budgetRepository.findByUserId(userId)
                    .orElseThrow(() -> new NotFoundException("User not found:"+userId));
            if(transaction.getAmount() > transactionDto.getAmount()){
                int result = (transaction.getAmount()-transactionDto.getAmount())+budget.getCurrent_balance();
                budget.setCurrent_balance(result);
                budgetRepository.save(budget);
            } else {
                int result = (transactionDto.getAmount()-transaction.getAmount())+budget.getCurrent_balance();
                budget.setCurrent_balance(result);
                budgetRepository.save(budget);
            }

        }

        transactionRepository.save(transaction);
        return "Edited transaction was successfully";
    }

    public String deleteTransaction(Long userId){
        transactionRepository.deleteByUserId(userId);
        return "Deleted transaction was successfully";
    }



}
