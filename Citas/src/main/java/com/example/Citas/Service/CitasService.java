package com.example.Citas.Service;

import com.example.Citas.Model.Citas;
import com.example.Citas.Repository.CitasRepository;
import com.example.Citas.WebClient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CitasService {

    @Autowired
    private CitasRepository citasRepository;

    @Autowired
    private WebClient webClient;

    /**
     * Crea un nuevo horario disponible o programa una cita.
     *
     * @param nuevoRegistro El objeto HorarioReserva que puede ser un slot disponible o una cita.
     * @return El registro guardado.
     * @throws RuntimeException si es una cita y la mascota no existe o el slot ya está ocupado.
     */
    public CitasService crearCitasService(Citas nuevoRegistro) {
        // Lógica para crear un slot disponible (mascotaId es null)
        if (nuevoRegistro.getMascotaId() == null) {
            // Asegurarse de que el slot esté marcado como disponible
            nuevoRegistro.setDisponible(true);
            return CitasRepository.save(nuevoRegistro);
        }
        // Lógica para programar una cita (mascotaId tiene valor)
        else {
            // 1. Validar que la mascota exista usando el WebClient
            Optional<Map<String, Object>> mascota = historialMedicoClient.getMascotaById(nuevoRegistro.getMascotaId());
            if (mascota.isEmpty()) {
                throw new RuntimeException("Mascota con ID " + nuevoRegistro.getMascotaId() + " no encontrada en el sistema de historial médico.");
            }

            // 2. Verificar si el slot específico está disponible para esta reserva
            // Buscamos un horario que coincida con especialista, fecha, hora y que esté disponible.
            Optional<HorarioReserva> slotExistente = horarioReservaRepository
                    .findByEspecialistaIdAndFechaAndHoraInicioAndHoraFinAndDisponibleTrue(
                            nuevoRegistro.getEspecialistaId(),
                            nuevoRegistro.getFecha(),
                            nuevoRegistro.getHoraInicio(),
                            nuevoRegistro.getHoraFin());

            if (slotExistente.isEmpty()) {
                throw new RuntimeException("No se encontró un slot disponible para el especialista " +
                        nuevoRegistro.getEspecialistaId() + " en la fecha " + nuevoRegistro.getFecha() +
                        " de " + nuevoRegistro.getHoraInicio() + " a " + nuevoRegistro.getHoraFin());
            }

            // 3. Marcar el slot existente como no disponible y actualizarlo
            HorarioReserva slotParaReservar = slotExistente.get();
            slotParaReservar.setMascotaId(nuevoRegistro.getMascotaId());
            slotParaReservar.setMotivoCita(nuevoRegistro.getMotivoCita());
            slotParaReservar.setDisponible(false); // Marcar como ocupado
            slotParaReservar.setEstadoReserva("CONFIRMADA"); // O "PENDIENTE"
            slotParaReservar.setMotivoBloqueo("Cita programada con Mascota ID: " + nuevoRegistro.getMascotaId());

            return horarioReservaRepository.save(slotParaReservar); // Guardar la reserva
        }
    }

    /**
     * Obtiene todos los horarios y reservas.
     *
     * @return Una lista de HorarioReserva.
     */
    public List<HorarioReserva> obtenerTodosLosRegistros() {
        return horarioReservaRepository.findAll();
    }

    /**
     * Obtiene solo los horarios que están disponibles.
     *
     * @return Una lista de HorarioReserva disponibles.
     */
    public List<HorarioReserva> obtenerHorariosDisponibles() {
        return horarioReservaRepository.findByDisponibleTrue();
    }

    /**
     * Obtiene horarios disponibles por ID de especialista y fecha.
     *
     * @param especialistaId ID del especialista.
     * @param fecha Fecha para buscar.
     * @return Una lista de HorarioReserva disponibles.
     */
    public List<HorarioReserva> obtenerHorariosDisponiblesPorEspecialistaYFecha(Long especialistaId, LocalDate fecha) {
        return horarioReservaRepository.findByEspecialistaIdAndFechaAndDisponibleTrue(especialistaId, fecha);
    }

    /**
     * Obtiene todas las citas programadas para una mascota específica.
     *
     * @param mascotaId ID de la mascota.
     * @return Una lista de HorarioReserva que son citas.
     */
    public List<HorarioReserva> obtenerReservasPorMascota(Long mascotaId) {
        return horarioReservaRepository.findByMascotaId(mascotaId);
    }
}
