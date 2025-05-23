package com.example.Rating.WebClient;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono; 

@Component 
public class ProductoServiceClient {

    private final WebClient webClient;//REEMPLAZAR ESTO POR EL PRODUCTO PARA QUE SE CONECTEN

    
   public ProductoClient(@Value("${producto-service.url}") String productoServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(productoServiceUrl).build();
    }

    public Map<String, Object> getProductoById(Long id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    System.out.println("Error 4xx al consultar producto con id: " + id + ", status: " + response.statusCode());
                    return response.bodyToMono(String.class)
                            .map(body -> new RuntimeException("Producto no encontrado o inválido: " + body));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    System.out.println("Error 5xx del servicio de productos al consultar id: " + id + ", status: " + response.statusCode());
                    return response.bodyToMono(String.class)
                            .map(body -> new RuntimeException("Error interno del servicio de productos: " + body));
                })
                .bodyToMono(Map.class)
                .block();
    }

}
