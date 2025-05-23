package com.microservicios.pagos.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component

public class OrdenClient {

    private final WebClient webcliente;




    public OrdenClient(@Value("${orden-service.url}")String ordenServiceUrl){
        this.webcliente= WebClient.builder().baseUrl(ordenServiceUrl).build();
    }

public Map<String, Object> getOrdenById(Long id) {
    return this.webcliente.get()
        .uri("/{id}", id)  // Solo {id}
        .retrieve()
        .onStatus(status -> status.is4xxClientError(), response -> {
            System.out.println("Error 4xx al consultar orden con id: " + id + ", status: " + response.statusCode());
            return response.bodyToMono(String.class)
                .map(body -> new RuntimeException("orden invalida: " + body));
        })
        .bodyToMono(Map.class)
        .block();
}





}
