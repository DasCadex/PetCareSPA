package com.example.Notificaciones.WebClient;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component


public class PagosClient {
    private final WebClient webcliente;

    public PagosClient (@Value("${pagos-service.url}")String pagosServiceUrl){
        this.webcliente =WebClient.builder().baseUrl(pagosServiceUrl).build();
    }

    public Map<String, Object> getPagosById(Long id){
        return this.webcliente.get()
            .uri("/{id}", id)  // Aquí se reemplaza {id} con el valor
            .retrieve()
            .onStatus(status -> status.is4xxClientError(),
                      response -> response.bodyToMono(String.class)
                              .map(body -> new RuntimeException("Pago inexistente")))
            .bodyToMono(Map.class)
            .block();  // Sincroniza la respuesta

    }







}
