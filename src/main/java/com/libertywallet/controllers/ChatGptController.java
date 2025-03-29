package com.libertywallet.controllers;


import com.libertywallet.models.ChatGptApiUsage;
import com.libertywallet.models.Recommendation;
import com.libertywallet.models.User;
import com.libertywallet.repositories.ApiUsageRepository;
import com.libertywallet.repositories.RecommendationRepository;
import com.libertywallet.repositories.UserRepository;
import com.libertywallet.services.ChatGptService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/chat")
public class ChatGptController {

    private final ApiUsageRepository apiUsageRepository;
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ChatGptService chatGptService;

    @Autowired
    public ChatGptController(UserRepository userRepository,ApiUsageRepository apiUsageRepository, RecommendationRepository recommendationRepository,ChatGptService chatGptService){
        this.userRepository = userRepository;
        this.recommendationRepository = recommendationRepository;
        this.apiUsageRepository = apiUsageRepository;
        this.chatGptService = chatGptService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askChatGpt(@RequestBody Map<String,String> request){
        try{
            String email = request.get("email");
            String message = request.get("message");
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if(!optionalUser.isPresent()){
                throw new RuntimeException("User not found!");
            }
            User user = optionalUser.get();

            String response = chatGptService.getResponse(message);
            ChatGptApiUsage chatGptApiUsage = new ChatGptApiUsage();
            chatGptApiUsage.setUser(user);
            apiUsageRepository.save(chatGptApiUsage);

            Recommendation recommendation = new Recommendation();
            recommendation.setChatGptApiUsage(chatGptApiUsage);
            recommendation.setRec_text(response);
            recommendationRepository.save(recommendation);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("ERROR message"+e.getMessage());
        }
    }
}
