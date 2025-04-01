package com.libertywallet.services;


import com.libertywallet.exception.UserNotFoundException;
import com.libertywallet.models.Recommendation;
import com.libertywallet.models.User;
import com.libertywallet.models.UserFeedback;
import com.libertywallet.repositories.RecommendationRepository;
import com.libertywallet.repositories.UserFeedBackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserFeedBackRepository userFeedBackRepository;
    public RecommendationService(RecommendationRepository recommendationRepository,UserFeedBackRepository userFeedBackRepository){
        this.recommendationRepository = recommendationRepository;
        this.userFeedBackRepository = userFeedBackRepository;

    }

    public List<UserFeedback> getFavoriteRecommendation(Long userId){
        List<UserFeedback>  feedbackList = userFeedBackRepository.findByUserIdAndFavoriteTrue(userId);
        log.info("Finding user favorite recommendation list");
        if(feedbackList.isEmpty()){
            throw new UserNotFoundException(""+userId);
        }
        return feedbackList;
    }

}
