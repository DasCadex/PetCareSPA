package com.example.HistorialMedico.Service;


import com.example.HistorialMedicoMascotas.Model.HistorialMedicoModel;
import com.example.HistorialMedicoMascotas.Repository.HistorialMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialMedicoService {

    private final HistorialMedicoRepository historialMedicoRepository;
    private final WebClient webClient;

    
    public HistorialMedicoService(HistorialMedicoRepository historialMedicoRepository, WebClient.Builder webClientBuilder, @Value("${webclient.base-url}") String webClientBaseUrl) {
        this.historialMedicoRepository = historialMedicoRepository;
        
        this.webClient = webClientBuilder.baseUrl(webClientBaseUrl).build();
    }

    

    public List<HistorialMedicoModel> getAllHistorialMedico() {
        return historialMedicoRepository.findAll();
    }

    public Optional<HistorialMedicoModel> getHistorialMedicoById(Long id) {
        return historialMedicoRepository.findById(id);
    }

    public HistorialMedicoModel saveHistorialMedico(HistorialMedicoModel historialMedico) {
        return historialMedicoRepository.save(historialMedico);
    }

    public HistorialMedicoModel updateHistorialMedico(Long id, HistorialMedicoModel historialMedicoDetails) {
        return historialMedicoRepository.findById(id)
                .map(historial -> {
                    historial.setMascotaId(historialMedicoDetails.getMascotaId());
                    historial.setFechaConsulta(historialMedicoDetails.getFechaConsulta());
                    historial.setMotivoConsulta(historialMedicoDetails.getMotivoConsulta());
                    historial.setDiagnostico(historialMedicoDetails.getDiagnostico());
                    historial.setTratamiento(historialMedicoDetails.getTratamiento());
                    historial.setVacunas(historialMedicoDetails.getVacunas());
                    return historialMedicoRepository.save(historial);
                }).orElse(null); 
    }

    public boolean deleteHistorialMedico(Long id) {
        if (historialMedicoRepository.existsById(id)) {
            historialMedicoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    

    
    public static class MascotaDto {
        private Long id;
        private String nombre;
        private String especie;
       
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getEspecie() { return especie; }
        public void setEspecie(String especie) { this.especie = especie; }
    }

    /**
     * Recupera los detalles de la Mascota usando webclient
     * @param mascotaId 
     * @return Un Mono que emite MascotaDto si se encuentra, o un Mono vacío.
     */
    public Mono<MascotaDto> getMascotaDetailsFromExternalService(Long mascotaId) {
        return webClient.get()
                .uri("/" + mascotaId) 
                .retrieve() 
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Error al obtener detalles de Mascota: " + clientResponse.statusCode())))
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Error del servidor al obtener detalles de Mascota: " + clientResponse.statusCode())))
                .bodyToMono(MascotaDto.class) 
                .doOnError(e -> System.err.println("Error al llamar al servicio de mascotas: " + e.getMessage())) 
                .onErrorResume(e -> Mono.empty()); // Devuelve un Mono vacío en caso de error
    }

  
}
