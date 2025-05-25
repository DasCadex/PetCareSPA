package com.example.ordenecompra.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PromocionClient {

    private final WebClient webcliente;

    


    public PromocionClient(@Value("${promocion-service.url}") String promocionServiceUrl){
        this.webcliente= WebClient.builder().baseUrl(promocionServiceUrl).build();

    }

     public Map<String, Object> getPromocionById(Long id) {
    return this.webcliente.get()
            .uri("/{id}", id)  // Aquí se reemplaza {id} con el valor
            .retrieve()
            .onStatus(status -> status.is4xxClientError(),
                      response -> response.bodyToMono(String.class)
                              .map(body -> new RuntimeException("promocion invalida ")))
            .bodyToMono(Map.class)
            .block();  // Sincroniza la respuesta
}




}
