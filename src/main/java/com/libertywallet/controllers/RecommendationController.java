package com.libertywallet.controllers;

import com.libertywallet.models.UserFeedback;
import com.libertywallet.services.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {

    public final RecommendationService recommendationService;
    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("/favorite_rec/{userId}")
    public ResponseEntity<List<UserFeedback>> favoriteRecommendation(@PathVariable Long userId){
        List<UserFeedback> favoriteRecList = recommendationService.getFavoriteRecommendation(userId);
        return ResponseEntity.ok(favoriteRecList);
    }
}
