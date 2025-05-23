package com.example.Soporte.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono; 

import java.util.Map;
import java.util.Optional;

@Component
public class ClienteClient {

    private final WebClient webClient;

    public ClienteClient(@Value("${cliente.service.url}") String clienteServiceUrl,
                         WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(clienteServiceUrl).build();
    }

    public Optional<Map<String, Object>> getClienteById(Long clienteId) {
        try {
            Map<String, Object> clienteData = this.webClient.get()
                .uri("/clientes/{id}", clienteId)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse -> {
                    System.err.println("Cliente no encontrado en el servicio de clientes (ID: " + clienteId + "). Status: " + clientResponse.statusCode());
                    return Mono.empty();
                })
                .bodyToMono(Map.class)
                .block(); // Bloquea aquí para obtener el resultado de forma síncrona

            return Optional.ofNullable(clienteData);
        } catch (Exception e) {
            System.err.println("Error al intentar obtener cliente del servicio de clientes (ID: " + clienteId + "): " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Map<String, Object>> getClienteByEmail(String email) {
        try {
            Map<String, Object> clienteData = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/clientes/search/findByEmail")
                                                .queryParam("email", email)
                                                .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse -> {
                    System.err.println("Cliente no encontrado por email en el servicio de clientes (Email: " + email + "). Status: " + clientResponse.statusCode());
                    return Mono.empty();
                })
                .bodyToMono(Map.class)
                .block(); // Bloquea aquí para obtener el resultado de forma síncrona

            return Optional.ofNullable(clienteData);
        } catch (Exception e) {
            System.err.println("Error al intentar obtener cliente por email del servicio de clientes (Email: " + email + "): " + e.getMessage());
            return Optional.empty();
        }
    }
}