package com.libertywallet.service;


import com.libertywallet.dto.RecommendationDto;
import com.libertywallet.dto.UserFeedbackDto;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.entity.Recommendation;
import com.libertywallet.entity.User;
import com.libertywallet.entity.UserFeedback;
import com.libertywallet.repository.RecommendationRepository;
import com.libertywallet.repository.UserFeedBackRepository;
import com.libertywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserFeedBackRepository userFeedBackRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public List<UserFeedback> getFavoriteRecommendation(Long userId){
        List<UserFeedback>  feedbackList = userFeedBackRepository.findByUserIdAndFavoriteTrue(userId);
        log.info("Finding user favorite recommendations");
        if(feedbackList.isEmpty()){
            throw new NotFoundException("Favorite recommendations not found (user id:"+userId+")");
        }
        return feedbackList;
    }

    public List<UserFeedbackDto> getLikedRecommendation(Long userId){
        List<UserFeedback> feedbackList = userFeedBackRepository.findByUserIdAndLikedTrue(userId);
        log.info("Finding user liked recommendations");

        if(feedbackList.isEmpty()){
            throw new NotFoundException("Liked recommendations not found (user id:"+userId+")");
        }

        return feedbackList.stream().map(f -> {
            UserFeedbackDto dto = new UserFeedbackDto();
            dto.setId(f.getId());
            dto.setUserId(f.getUser().getId());
            dto.setRecommendationId(f.getRecommendation().getId());
            dto.setLiked(f.getLiked());
            dto.setFavorite(f.getFavorite());
            dto.setRecommendationText(f.getRecommendation().getText());
            dto.setRecommendationCategory(f.getRecommendation().getCategory());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<RecommendationDto> getPopularRecommendations(){
        Pageable pageable = PageRequest.of(0, 10);
        List<Recommendation> recommendations = recommendationRepository.findTopRecommendationsWithLikes(pageable);
        return recommendations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private RecommendationDto convertToDTO(Recommendation recommendation) {
        RecommendationDto dto = new RecommendationDto();
        dto.setCategory(recommendation.getCategory());
        dto.setText(recommendation.getText());
        return dto;
    }
    public List<RecommendationDto> getPersonalizedRecommendations(Long userId){
        List<Recommendation> recommendations = recommendationRepository.findRecommendationsByUserPreferences(userId);
        return recommendations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void giveFeedBack(Long userId,Long recommendationId,Boolean favorite,Boolean liked){
        UserFeedback userFeedback = new UserFeedback();
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new NotFoundException("Recommendation not found(recommendation id:"+recommendationId+")"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found(user id:"+userId+")"));
        userFeedback.setUser(user);
        userFeedback.setRecommendation(recommendation);
        userFeedback.setLiked(liked);
        userFeedback.setFavorite(favorite);
    }

}
