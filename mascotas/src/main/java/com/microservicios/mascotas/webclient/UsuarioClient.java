package com.microservicios.mascotas.webclient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component

public class UsuarioClient {

    private final WebClient webclient;



    public UsuarioClient (@Value("${usuario-service.url}")String usuarioServiceUrl){
        this.webclient= WebClient.builder().baseUrl(usuarioServiceUrl).build();


    }
    public Map<String, Object> getUsuarioById(Long id) {
    return this.webclient.get()
            .uri("/{id}", id)  // Aquí se reemplaza {id} con el valor
            .retrieve()
            .onStatus(status -> status.is4xxClientError(),
                      response -> response.bodyToMono(String.class)
                              .map(body -> new RuntimeException("Usuario invalido ")))
            .bodyToMono(Map.class)
            .block();  // Sincroniza la respuesta
}

}
