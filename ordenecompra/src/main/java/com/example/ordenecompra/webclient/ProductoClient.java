package com.example.ordenecompra.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductoClient {

    private final WebClient webcliente;

    @Value("${producto-service.url}")
    private String productoServiceUrl;

    public ProductoClient(@Value("${producto-service.url}") String productoServiceUrl) {
        this.webcliente = WebClient.builder().baseUrl(productoServiceUrl).build();
    }

    public Map<String, Object> getProductoById(Long id) {
        // Realiza la petición al microservicio de productos
        return this.webcliente.get()
            .uri("/{id}", id)  // Accede al producto por ID
            .retrieve()
            .onStatus(status -> status.is4xxClientError(),
                response -> response.bodyToMono(String.class)
                    .map(body -> new RuntimeException("Producto inexistente " + id)))
            .bodyToMono(Map.class)
            .block();  // Bloquea hasta obtener la respuesta
    }
}

