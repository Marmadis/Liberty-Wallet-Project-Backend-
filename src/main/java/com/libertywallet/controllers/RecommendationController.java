package com.libertywallet.controllers;

import com.libertywallet.dto.FeedBackRequest;
import com.libertywallet.dto.RecommendationDTO;
import com.libertywallet.dto.UserFeedbackDTO;
import com.libertywallet.models.UserFeedback;
import com.libertywallet.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {

    public final RecommendationService recommendationService;


    @GetMapping("/favorite_rec/{userId}")
    public ResponseEntity<List<UserFeedback>> favoriteRecommendation(@PathVariable Long userId){
        List<UserFeedback> favoriteRecList = recommendationService.getFavoriteRecommendation(userId);
        return ResponseEntity.ok(favoriteRecList);
    }

    @GetMapping("/liked_rec/{userId}")
    public ResponseEntity<List<UserFeedbackDTO>> likedRecommendation(@PathVariable Long userId){
        List<UserFeedbackDTO> likedRecList = recommendationService.getLikedRecommendation(userId);
        return ResponseEntity.ok(likedRecList);
    }

    @GetMapping("/popular")
    public List<RecommendationDTO> getPopularRecommendations() {
        return recommendationService.getPopularRecommendations();
    }

    @GetMapping("/personalized/{userId}")
    public List<RecommendationDTO> getPersonalizedRecommendations(@PathVariable Long userId) {
        return recommendationService.getPersonalizedRecommendations(userId);
    }

    @PostMapping("/feedback/{userId}")
    public void giveFeedback(@PathVariable Long userId,@RequestBody FeedBackRequest feedBackRequest){
        Long recommendationId = feedBackRequest.getRecommendationId();
        Boolean liked = feedBackRequest.getLiked();
        Boolean favorite = feedBackRequest.getFavorite();
        recommendationService.giveFeedBack(userId,recommendationId,favorite,liked);
    }
}
