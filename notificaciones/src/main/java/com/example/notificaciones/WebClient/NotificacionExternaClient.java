package com.example.notificaciones.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono; 

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificacionExternaClient {

    private final WebClient webClient;

    
    public NotificacionExternaClient(@Value("${external.notification.service.url}") String externalNotificationServiceUrl,
                                     WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(externalNotificationServiceUrl).build();
    }

    
     /**
      @param tipo
      @param destinatario 
      @param mensaje
      @param referenciaExternaId 
      @return 
     */
    
    public boolean enviarNotificacionExterna(String tipo, String destinatario, String mensaje, String referenciaExternaId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", tipo);
        payload.put("to", destinatario);
        payload.put("message", mensaje);
        payload.put("externalRefId", referenciaExternaId);

        try {          
            this.webClient.post()
                .uri("/send") 
                .bodyValue(payload)
                .retrieve()               
                .onStatus(status -> status.isError(), clientResponse ->
                    clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException(
                            "Error al enviar notificación externa: " + clientResponse.statusCode() + " - " + errorBody
                        )))
                )
                .bodyToMono(Void.class) 
                .block(); 

            System.out.println("Notificación externa enviada exitosamente para destinatario: " + destinatario);
            return true; 
        } catch (Exception e) {
            System.err.println("Fallo al enviar notificación externa a " + destinatario + ": " + e.getMessage());
            return false; 
        }
    }
}
