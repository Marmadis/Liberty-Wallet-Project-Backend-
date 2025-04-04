package com.libertywallet.services;


import com.libertywallet.exception.NotFoundException;
import com.libertywallet.models.Category;
import com.libertywallet.models.Transaction;
import com.libertywallet.models.User;
import com.libertywallet.repositories.TransactionRepository;
import com.libertywallet.repositories.UserRepository;
import com.libertywallet.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    public List<Transaction> getAllTransaction(Long userId){
        log.info("Getting all transaction info");
        List<Transaction> transactionList = transactionRepository.findByUserId(userId);
        if(transactionList.isEmpty()){
            log.warn("transaction list is empty");
        }
        log.info("Got all transaction info");
        return  transactionList;
    }

    public Transaction createTransaction(Long userId, Long categoryId, BigDecimal amount,String description ,LocalDate date){
        log.info("Creating new transaction...");
        Transaction transaction = new Transaction();
        Category category = categoryRepository.findById(categoryId)
          .orElseThrow(() ->  new NotFoundException("Category not found (user id:"+categoryId+")"));
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new NotFoundException("User not found (user id:"+userId+")"));
        transaction.setAmount(amount);
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setDate(date);
        return transactionRepository.save(transaction);
    }
}
