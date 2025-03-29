package com.libertywallet.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatGptService {
    private static final String API_URL = System.getenv("OPENAI_API_URL");
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");


    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getResponse(String message) throws IOException {
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

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException(" ERROR API: " + response);
        }

        JsonNode jsonNode = objectMapper.readTree(response.body().string());
        return jsonNode.get("choices").get(0).get("message").get("content").asText();
    }


}
