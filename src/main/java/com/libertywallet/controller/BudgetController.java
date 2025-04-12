package com.libertywallet.controller;


import com.libertywallet.dto.BudgetDto;
import com.libertywallet.request.BudgetRequest;
import com.libertywallet.service.BudgetService;
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
    public ResponseEntity<BudgetDto> getBudgetInfo(@PathVariable Long userId){
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

    @GetMapping("/delete/{userId}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long userId){
        return ResponseEntity.ok(budgetService.deleteBudget(userId));
    }

    @GetMapping("/edit/{userId}")
    public ResponseEntity<String> editBudget(@PathVariable Long userId, @RequestBody BudgetDto budgetDto){
        return ResponseEntity.ok(budgetService.editBudget(userId,budgetDto));
    }
}
