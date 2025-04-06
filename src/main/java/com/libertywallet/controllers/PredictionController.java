package com.libertywallet.controllers;


import com.libertywallet.services.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/predict")
public class PredictionController {

    private  final ForecastService forecastService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<Double> getForecast(@PathVariable Long userId){
        return ResponseEntity.ok(forecastService.forecastNextMonthExpenses(userId));
    }
}

