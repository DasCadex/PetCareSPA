package com.example.Notificaciones.WebClient;

import com.example.Notificaciones.Client.model.Pago; // Importa el modelo Pago
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PagosServiceClient {

    private final WebClient webClient;

    // Inyecta la URL base del servicio de pagos desde application.properties
    public PagosServiceClient(WebClient.Builder webClientBuilder,
                              @Value("${pagos.service.url}") String pagosServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(pagosServiceUrl).build();
    }

    public Mono<Pago> obtenerDetallesPago(Long idpago) {
        return webClient.get()
                .uri("/{id}", idpago) // Asume un endpoint como /api/pagos/{id}
                .retrieve()
                // Manejo de errores para 404 (Pago no encontrado)
                .onStatus(HttpStatus.NOT_FOUND::equals,
                          response -> Mono.error(new RuntimeException("Pago con ID " + idpago + " no encontrado en el servicio de pagos.")))
                // Manejo general de otros errores HTTP (4xx, 5xx)
                .onStatus(HttpStatus::isError,
                          response -> response.bodyToMono(String.class)
                                              .flatMap(errorBody -> Mono.error(new RuntimeException("Error del servicio de pagos al obtener ID " + idpago + ": " + errorBody))))
                .bodyToMono(Pago.class); // Convierte la respuesta JSON a un objeto Pago
    }
}


