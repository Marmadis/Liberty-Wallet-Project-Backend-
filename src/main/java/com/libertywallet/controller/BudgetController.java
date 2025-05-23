package com.libertywallet.controller;


import com.libertywallet.dto.BudgetDto;
import com.libertywallet.service.BudgetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/budget")
public class BudgetController {

    private  final  BudgetService budgetService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<BudgetDto> getBudgetInfo(@PathVariable UUID userId){
        log.info("User request budget info");
        return ResponseEntity.ok(budgetService.getBudgetInformation(userId));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createBudgetInfo(@PathVariable UUID userId, @RequestBody BudgetDto budgetDto){
        return ResponseEntity.ok(budgetService.createBudgetInformation(userId,budgetDto));
    }

    @GetMapping("/delete/{userId}")
    public ResponseEntity<String> deleteBudget(@PathVariable UUID userId){
        return ResponseEntity.ok(budgetService.deleteBudget(userId));
    }

    @GetMapping("/edit/{userId}")
    public ResponseEntity<String> editBudget(@PathVariable UUID userId, @RequestBody BudgetDto budgetDto){
        return ResponseEntity.ok(budgetService.editBudget(userId,budgetDto));
    }
}
