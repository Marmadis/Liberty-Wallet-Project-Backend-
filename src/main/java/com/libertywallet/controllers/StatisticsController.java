package com.libertywallet.controllers;

import com.libertywallet.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserStatistics(@PathVariable String email) {
        return ResponseEntity.ok(statisticsService.getUserExpenseAnalysis(email));
    }
}