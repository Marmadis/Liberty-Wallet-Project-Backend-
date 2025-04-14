package com.libertywallet.controller;


import com.libertywallet.dto.TransactionDto;
import com.libertywallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;


    @GetMapping("/get/{userId}")
    public ResponseEntity<List<TransactionDto>> getTransaction(@PathVariable UUID userId){

        return ResponseEntity.ok(transactionService.getAllTransaction(userId));
    }

    @PostMapping("/create/{categoryId}/{userId}")
    public ResponseEntity<String> createTransaction(@PathVariable UUID userId,@PathVariable UUID categoryId, @RequestBody TransactionDto transactionDto){


        return ResponseEntity.ok(transactionService.createTransaction(userId,categoryId,transactionDto));
    }

    @PostMapping("/edit/{userId}/{transactionId}")
    public ResponseEntity<String> editTransaction(@PathVariable UUID userId,@PathVariable UUID transactionId,@RequestBody TransactionDto transactionDto){
        return ResponseEntity.ok(transactionService.editTransaction(userId,transactionId,transactionDto));
    }

    @GetMapping("/delete/{userId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable UUID userId){
        return ResponseEntity.ok(transactionService.deleteTransaction(userId));
    }
}
