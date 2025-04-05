package com.libertywallet.controllers;


import com.libertywallet.dto.TransactionRequest;
import com.libertywallet.models.Transaction;
import com.libertywallet.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;


    @GetMapping("/getall/{userId}")
    public ResponseEntity<List<Transaction>> getAllTransaction(@PathVariable Long userId){
        List<Transaction> transactionList = transactionService.getAllTransaction(userId);
        return ResponseEntity.ok(transactionList);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createTransaction(@PathVariable Long userId, TransactionRequest transactionRequest){

        transactionService.createTransaction(
                userId,
                transactionRequest.getCategoryId(),
                transactionRequest.getAmount(),
                transactionRequest.getDescription(),
                transactionRequest.getDate()
        );

        return ResponseEntity.ok("New transaction added successfully");
    }
}
