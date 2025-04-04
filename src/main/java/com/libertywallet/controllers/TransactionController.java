package com.libertywallet.controllers;


import com.libertywallet.models.Transaction;
import com.libertywallet.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

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
}
