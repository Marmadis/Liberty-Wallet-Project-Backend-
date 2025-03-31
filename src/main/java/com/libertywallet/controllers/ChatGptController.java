package com.libertywallet.controllers;


import com.libertywallet.exception.EmailNotFoundException;
import com.libertywallet.models.ChatGptApiUsage;
import com.libertywallet.models.Recommendation;
import com.libertywallet.models.User;
import com.libertywallet.repositories.ApiUsageRepository;
import com.libertywallet.repositories.RecommendationRepository;
import com.libertywallet.repositories.UserRepository;
import com.libertywallet.services.ChatGptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Optional;

@Slf4j
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

            String email = request.get("email");
            String message = request.get("message");
            Optional<User> optionalUser = userRepository.findByEmail(email);

            User user = optionalUser.orElseThrow(() -> new EmailNotFoundException(email));

            String response = chatGptService.getResponse(message);
            log.info("Get  ChatGPT response");
            ChatGptApiUsage chatGptApiUsage = new ChatGptApiUsage();
            chatGptApiUsage.setUser(user);
            apiUsageRepository.save(chatGptApiUsage);
            log.info("Save ChatGPT response in DB");

            Recommendation recommendation = new Recommendation();
            recommendation.setChatGptApiUsage(chatGptApiUsage);
            recommendation.setRec_text(response);
            recommendationRepository.save(recommendation);
            log.info("Save ChatGPT recommendation in DB");
            return ResponseEntity.ok(response);


    }
}
