package com.libertywallet.controller;

import com.libertywallet.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserStatistics(@PathVariable UUID userId) {
        return ResponseEntity.ok(statisticsService.getUserExpenseAnalysis(userId));
    }
}