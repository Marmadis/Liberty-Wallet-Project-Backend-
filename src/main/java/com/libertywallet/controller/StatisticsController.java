package com.libertywallet.controller;

import com.libertywallet.dto.ForecastDto;
import com.libertywallet.service.ForecastService;
import com.libertywallet.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {


    private final ForecastService forecastService;
    private final StatisticsService statisticsService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserStatistics(@PathVariable UUID userId) {
        return ResponseEntity.ok(statisticsService.getUserExpenseAnalysis(userId));
    }

    @GetMapping("/get/alltime/{userId}")
    public ResponseEntity<List<ForecastDto>> getUserEveryMonthSum(@PathVariable UUID userId){
        return ResponseEntity.ok(forecastService.getAllMonthsStatistics(userId));
    }
}