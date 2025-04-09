package com.libertywallet.repository;


import com.libertywallet.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PredictionRepository extends JpaRepository<Prediction,Long> {

}
