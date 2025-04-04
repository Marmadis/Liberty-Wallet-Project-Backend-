package com.libertywallet.controllers;


import com.libertywallet.dto.TransactionRequest;
import com.libertywallet.models.Transaction;
import com.libertywallet.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/getall/{userId}")
    public ResponseEntity<List<Transaction>> getAllTransaction(@PathVariable Long userId){
        List<Transaction> transactionList = transactionService.getAllTransaction(userId);
        return ResponseEntity.ok(transactionList);
    }

    @PostMapping("/set/{userId}")
    public ResponseEntity<String> setTransaction(@PathVariable Long userId, TransactionRequest transactionRequest){

        transactionService.setTransaction(
                userId,
                transactionRequest.getCategoryId(),
                transactionRequest.getAmount(),
                transactionRequest.getDescription(),
                transactionRequest.getDate()
        );

        return ResponseEntity.ok("New transaction added successfully");
    }
}
