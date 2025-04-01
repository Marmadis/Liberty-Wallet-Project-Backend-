package com.libertywallet.services;


import com.libertywallet.dto.RecommendationDTO;
import com.libertywallet.dto.UserFeedbackDTO;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.models.Recommendation;
import com.libertywallet.models.User;
import com.libertywallet.models.UserFeedback;
import com.libertywallet.repositories.RecommendationRepository;
import com.libertywallet.repositories.UserFeedBackRepository;
import com.libertywallet.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserFeedBackRepository userFeedBackRepository;
    private final UserRepository userRepository;
    public RecommendationService(RecommendationRepository recommendationRepository, UserFeedBackRepository userFeedBackRepository, UserRepository userRepository){
        this.recommendationRepository = recommendationRepository;
        this.userFeedBackRepository = userFeedBackRepository;
        this.userRepository = userRepository;

    }

    public List<UserFeedback> getFavoriteRecommendation(Long userId){
        List<UserFeedback>  feedbackList = userFeedBackRepository.findByUserIdAndFavoriteTrue(userId);
        log.info("Finding user favorite recommendations");
        if(feedbackList.isEmpty()){
            throw new NotFoundException("Favorite recommendations not found (user id:"+userId+")");
        }
        return feedbackList;
    }

    public List<UserFeedbackDTO> getLikedRecommendation(Long userId){
        List<UserFeedback> feedbackList = userFeedBackRepository.findByUserIdAndLikedTrue(userId);
        log.info("Finding user liked recommendations");

        if(feedbackList.isEmpty()){
            throw new NotFoundException("Liked recommendations not found (user id:"+userId+")");
        }

        return feedbackList.stream().map(f -> {
            UserFeedbackDTO dto = new UserFeedbackDTO();
            dto.setId(f.getId());
            dto.setUserId(f.getUser().getId());
            dto.setRecommendationId(f.getRecommendation().getId());
            dto.setLiked(f.getLiked());
            dto.setFavorite(f.getFavorite());
            return dto;
        }).collect(Collectors.toList());
    }


    public List<RecommendationDTO> getPopularRecommendations(){
        Pageable pageable = PageRequest.of(0, 10);
        List<Recommendation> recommendations = recommendationRepository.findTopRecommendationsWithLikes(pageable);
        return recommendations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private RecommendationDTO convertToDTO(Recommendation recommendation) {
        RecommendationDTO dto = new RecommendationDTO();
        dto.setCategory(recommendation.getCategory());
//        dto.setImage(recommendation.getImage());
        dto.setText(recommendation.getText());
        return dto;
    }
    public List<RecommendationDTO> getPersonalizedRecommendations(Long userId){
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
        userFeedBackRepository.save(userFeedback);
    }

}
