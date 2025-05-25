package com.example.HistorialMedico.WebClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component

public class MascotasClient {

    private final WebClient webclient;

    public MascotasClient (@Value("${mascotas-service.url}") String mascotasServiceUrl){
        this.webclient= WebClient.builder().baseUrl(mascotasServiceUrl).build();

    }

    public Map<String, Object> getMascotasById(Long id) {
    return this.webclient.get()
            .uri("/{id}", id)  // Aquí se reemplaza {id} con el valor
            .retrieve()
            .onStatus(status -> status.is4xxClientError(),
                      response -> response.bodyToMono(String.class)
                              .map(body -> new RuntimeException("Mascota inexistente ")))
            .bodyToMono(Map.class)
            .block();  // Sincroniza la respuesta
}



}
