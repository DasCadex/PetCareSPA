package com.example.HistorialMedico.WebClient;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component

public class ClientCliente {

    private final WebClient webClient;

    public MascotaServiceClient(WebClient.Builder webClientBuilder,
                                @Value("${mascotas.service.url}") String mascotasServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(mascotasServiceUrl).build();
    }

    public Mono<Mascota> obtenerDetallesMascota(Long mascotaId) {
        return webClient.get()
                .uri("/{id}", mascotaId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                          response -> Mono.error(new RuntimeException("Mascota con ID " + mascotaId + " no encontrada en el servicio de mascotas.")))
                .onStatus(HttpStatus::isError,
                          response -> response.bodyToMono(String.class)
                                              .flatMap(errorBody -> Mono.error(new RuntimeException("Error del servicio de mascotas al obtener la mascota " + mascotaId + ": " + errorBody))))
                .bodyToMono(Mascota.class);
    }
}





