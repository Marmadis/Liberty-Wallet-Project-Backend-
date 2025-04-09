package com.libertywallet.controller;

import com.libertywallet.service.StatisticsService;
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