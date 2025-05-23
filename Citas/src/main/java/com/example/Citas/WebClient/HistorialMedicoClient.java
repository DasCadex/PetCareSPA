package com.example.Citas.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Component
public class HistorialMedicoClient {

    private final WebClient webClient;

    public HistorialMedicoClient(@Value("${historial-medico.service.url}") String historialMedicoServiceUrl,
                                 WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(historialMedicoServiceUrl).build();
    }

    /**
     * 
     * 
     * 
     *
     * @param mascotaId 
     * @return Un Optional que contiene un Map con los datos de la mascota si existe, o vacío si no.
     */
    public Optional<Map<String, Object>> getMascotaById(Long mascotaId) {
        try {
            Map<String, Object> mascotaData = this.webClient.get()
                .uri("/mascotas/{id}", mascotaId) // Endpoint hipotético en el servicio de Historial Médico
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse -> {
                    System.err.println("Mascota no encontrada en el módulo de Historial Médico (ID: " + mascotaId + "). Status: " + clientResponse.statusCode());
                    return Mono.empty();
                })
                .bodyToMono(Map.class)
                .block();

            return Optional.ofNullable(mascotaData);
        } catch (Exception e) {
            System.err.println("Error al intentar obtener mascota del módulo de Historial Médico (ID: " + mascotaId + "): " + e.getMessage());
            return Optional.empty();
        }
    }
}
