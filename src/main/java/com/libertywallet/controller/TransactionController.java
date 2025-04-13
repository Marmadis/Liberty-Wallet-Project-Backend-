package com.libertywallet.controller;


import com.libertywallet.dto.TransactionDto;
import com.libertywallet.service.TransactionService;
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


    @GetMapping("/get/{userId}")
    public ResponseEntity<List<TransactionDto>> getTransaction(@PathVariable Long userId){

        return ResponseEntity.ok(transactionService.getAllTransaction(userId));
    }

    @PostMapping("/create/{categoryId}/{userId}")
    public ResponseEntity<String> createTransaction(@PathVariable Long userId,@PathVariable Long categoryId, @RequestBody TransactionDto transactionDto){


        return ResponseEntity.ok(transactionService.createTransaction(userId,categoryId,transactionDto));
    }

    @PostMapping("/edit/{userId}/{transactionId}")
    public ResponseEntity<String> editTransaction(@PathVariable Long userId,@PathVariable Long transactionId,@RequestBody TransactionDto transactionDto){
        return ResponseEntity.ok(transactionService.editTransaction(userId,transactionId,transactionDto));
    }

    @GetMapping("/delete/{userId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long userId){
        return ResponseEntity.ok(transactionService.deleteTransaction(userId));
    }
}
