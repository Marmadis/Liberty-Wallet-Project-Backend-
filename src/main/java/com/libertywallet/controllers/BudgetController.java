package com.libertywallet.controllers;


import com.libertywallet.dto.BudgetRequest;
import com.libertywallet.models.Budget;
import com.libertywallet.services.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/budget")
public class BudgetController {

    private  final  BudgetService budgetService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<Budget> getBudgetInfo(@PathVariable Long userId){
        log.info("User request budget info");
        return ResponseEntity.ok(budgetService.getBudgetInformation(userId));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createBudgetInfo(@PathVariable Long userId, @RequestBody BudgetRequest budgetRequest){
        budgetService.createBudgetInformation(
                userId,
                budgetRequest.getAmountLimit(),
                budgetRequest.getCurrent_balance(),
                budgetRequest.getStart_date(),
                budgetRequest.getEnd_date()
                );
        return ResponseEntity.ok("Budget information successfully written");
    }
}
