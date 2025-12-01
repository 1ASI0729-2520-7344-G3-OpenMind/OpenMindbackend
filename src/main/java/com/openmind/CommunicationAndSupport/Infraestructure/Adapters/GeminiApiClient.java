package com.openmind.CommunicationAndSupport.Infraestructure.Adapters;

import com.openmind.CommunicationAndSupport.Domain.Model.ValueObjects.chatbotResponse;
import com.openmind.CommunicationAndSupport.Domain.Repositories.IChatbotExternalClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class GeminiApiClient implements IChatbotExternalClient {
    @Value("${ai.gemini.api-key}")
    private String apiKey;

    @Value("${ai.gemini.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public chatbotResponse ask(String conversationId, String text, String context) {
        String prompt = context + "\n\nUsuario: " + text;
        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey;

        String requestBody = """
            {
              "contents": [{
                "role": "user",
                "parts": [{"text": "%s"}]
              }]
            }
            """.formatted(prompt.replace("\"", "\\\""));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || !body.containsKey("candidates")) {
                return new chatbotResponse("Lo siento, no pude procesar tu mensaje.", 0.0);
            }

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");

            String reply = parts.get(0).get("text");
            return new chatbotResponse(reply, 1.0);

        } catch (Exception e) {
            System.err.println("GEMINI API ERROR: " + e.getMessage());
            return new chatbotResponse("Error interno del bot. Intenta de nuevo.", 0.0);
        }
    }
}
