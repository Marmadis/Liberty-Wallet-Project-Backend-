package com.libertywallet.repository;


import com.libertywallet.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PredictionRepository extends JpaRepository<Prediction, UUID> {

}
