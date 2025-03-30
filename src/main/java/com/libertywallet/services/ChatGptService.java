package com.libertywallet.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libertywallet.exception.ChatGptServiceException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ChatGptService {
    private static final String API_URL = "MISSING";
    private static final String API_KEY = "MISSING";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getResponse(String message) {
        log.info("Sending request for ChatGPT: {}", message);

        String jsonRequest = "{"
                + "\"model\":\"gpt-3.5-turbo\","
                + "\"messages\":[{\"role\":\"system\",\"content\":\"Ты финансовый консультант. Твоя задача — давать рекомендации по управлению личными финансами на основе теста.\"},"
                + "{\"role\":\"user\",\"content\":\"" + message + "\"}],"
                + "\"temperature\":0.3"
                + "}";

        RequestBody body = RequestBody.create(jsonRequest, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            log.info("Response from ChatGPT recevied,code answer: {}", response.code());

            if (!response.isSuccessful()) {
                throw new ChatGptServiceException("error API: " + response.code() + " " + response.message());
            }

            String responseBody = response.body().string();
            log.info("Body response from ChatGPT: {}", responseBody);

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String gptResponse = jsonNode.get("choices").get(0).get("message").get("content").asText();

            log.info("Response parsing successful, text recommendation: {}", gptResponse);
            return gptResponse;
        } catch (IOException e) {

            throw new ChatGptServiceException("Error parsing response from ChatGPT: " + e.getMessage());
        }
    }
}
