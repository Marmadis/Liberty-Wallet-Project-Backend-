package com.libertywallet.services;


import com.libertywallet.repositories.PredictionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PredictionService {

    private  final PredictionRepository predictionRepository;
    public PredictionService(PredictionRepository predictionRepository){
        this.predictionRepository = predictionRepository;
    }



}
