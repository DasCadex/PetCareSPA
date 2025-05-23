package com.example.HistorialMedico.WebClient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class MascotaClient {

    private final WebClient webClient;

    
    public MascotaClient(@Value("${mascota-service.url}") String mascotaServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(mascotaServiceUrl) 
                .build();
    }

    
    public Map<String, Object> getMascotaById(Long id) {
        return this.webClient.get()
            .uri("/{id}", id) 
            .retrieve()
            .onStatus(
                status -> status.is4xxClientError(), // Si es un error 4XX (ej. 404 Not Found)
                response -> response.bodyToMono(String.class)
                                   .map(body -> new RuntimeException("Mascota no encontrada (ID: " + id + "). Error: " + body))
            )
            .bodyToMono(Map.class) 
            .block(); 
    }
}
