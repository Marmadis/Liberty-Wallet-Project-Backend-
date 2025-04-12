package com.libertywallet.controller;


import com.libertywallet.dto.TransactionDto;
import com.libertywallet.request.TransactionRequest;
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


    @GetMapping("/getall/{userId}")
    public ResponseEntity<List<TransactionDto>> getAllTransaction(@PathVariable Long userId){

        return ResponseEntity.ok(transactionService.getAllTransaction(userId));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createTransaction(@PathVariable Long userId, @RequestBody TransactionRequest transactionRequest){
        transactionService.createTransaction(
                userId,
                transactionRequest.getCategoryId(),
                transactionRequest.getAmount(),
                transactionRequest.getDescription(),
                transactionRequest.getDate()
        );

        return ResponseEntity.ok("New transaction added successfully");
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
