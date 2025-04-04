package com.libertywallet.controllers;


import com.libertywallet.dto.BudgetRequest;
import com.libertywallet.models.Budget;
import com.libertywallet.services.BudgetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/budget")
public class BudgetController {

    private  final  BudgetService budgetService;

    public BudgetController(BudgetService budgetService){
        this.budgetService = budgetService;
    }

    @GetMapping("/getinfo/{userId}")
    public ResponseEntity<Budget> getBudgetInfo(@PathVariable Long userId){
        log.info("User request budget info");
        return ResponseEntity.ok(budgetService.getBudgetInformation(userId));
    }

    @PostMapping("/setinfo/{userId}")
    public ResponseEntity<String> setBudgetInfo(@PathVariable Long userId, @RequestBody BudgetRequest budgetRequest){
        budgetService.setBudgetInformation(
                userId,
                budgetRequest.getAmountLimit(),
                budgetRequest.getCurrent_balance(),
                budgetRequest.getStart_date(),
                budgetRequest.getEnd_date()
                );
        return ResponseEntity.ok("Budget information successfully written");
    }
}
