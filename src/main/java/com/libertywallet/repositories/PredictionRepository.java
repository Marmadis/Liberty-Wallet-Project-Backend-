package com.libertywallet.repositories;


import com.libertywallet.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PredictionRepository extends JpaRepository<Prediction,Long> {

}
