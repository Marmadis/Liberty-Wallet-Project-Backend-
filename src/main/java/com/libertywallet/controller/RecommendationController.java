package com.libertywallet.controller;

import com.libertywallet.dto.FeedBackDto;
import com.libertywallet.dto.RecommendationDto;
import com.libertywallet.dto.UserFeedbackDto;
import com.libertywallet.entity.UserFeedback;
import com.libertywallet.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {

    public final RecommendationService recommendationService;


    @GetMapping("/favorite_rec/{userId}")
    public ResponseEntity<List<UserFeedback>> favoriteRecommendation(@PathVariable UUID userId){
        List<UserFeedback> favoriteRecList = recommendationService.getFavoriteRecommendation(userId);
        return ResponseEntity.ok(favoriteRecList);
    }

    @GetMapping("/liked_rec/{userId}")
    public ResponseEntity<List<UserFeedbackDto>> likedRecommendation(@PathVariable UUID userId){
        List<UserFeedbackDto> likedRecList = recommendationService.getLikedRecommendation(userId);
        return ResponseEntity.ok(likedRecList);
    }

    @GetMapping("/popular")
    public List<RecommendationDto> getPopularRecommendations() {
        return recommendationService.getPopularRecommendations();
    }

    @GetMapping("/personalized/{userId}")
    public List<RecommendationDto> getPersonalizedRecommendations(@PathVariable UUID userId) {
        return recommendationService.getPersonalizedRecommendations(userId);
    }

    @PostMapping("/feedback/{userId}")
    public void giveFeedback(@PathVariable UUID userId,@RequestBody FeedBackDto feedBackRequest){
        UUID recommendationId = feedBackRequest.getRecommendationId();
        boolean liked = feedBackRequest.getLiked();
        boolean favorite = feedBackRequest.getFavorite();
        recommendationService.giveFeedBack(userId,recommendationId,favorite,liked);
    }
}
