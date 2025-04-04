package com.libertywallet.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/predict")
public class PredictionController {


    @GetMapping("/get/{userId}")
    public ResponseEntity<String> getPredict(@PathVariable Long userId){
        Long id = userId;
        return ResponseEntity.ok("Process");
    }
}
