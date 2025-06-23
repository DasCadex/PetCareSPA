package com.example.Soporte.Service;

import com.example.Soporte.Model.Soporte;
import com.example.Soporte.Repository.SoporteRepository;
import com.example.Soporte.WebClient.UsuarioClient;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con las solicitudes de soporte.
 * Incluye creación, consulta, eliminación y filtrado por usuario.
*/

@Service
@Transactional
public class SoporteService {

    @Autowired
    private SoporteRepository solicitudSoporteRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    /**
     * Crea una nueva solicitud de soporte, validando previamente que el usuario exista,
     * tenga los datos necesarios (nombre, correo) y que su rol sea "Soporte y administrador de sistemas".
     *
     * @param nuevasolicitud Objeto {@link Soporte} con los datos de la solicitud.
     * @return La solicitud guardada en la base de datos.
     * @throws RuntimeException si el usuario no existe, su rol es incorrecto o faltan datos.
     */
    // Método para crear una nueva solicitud de soporte (POST)
    public Soporte crearSolicitud(Soporte nuevasolicitud) {
        System.out.println("Buscando usuario con ID: " + nuevasolicitud.getUsuarioId());

        Map<String, Object> usuario = usuarioClient.getUsuarioById(nuevasolicitud.getUsuarioId());

        if (usuario == null || usuario.isEmpty()) {
            throw new RuntimeException("Usuario soporte no existe");
        }

        System.out.println("Usuario recibido: " + usuario);

        String nombreusuario = (String) usuario.get("username");
        if (nombreusuario == null) {
            throw new RuntimeException("No existe un soporte con este nombre");
        }

        String correousuario = (String) usuario.get("correo");
        if (correousuario == null) {
            throw new RuntimeException("Correo del soporte inválido o inexistente");
        }

        Map<String, Object> rolMap = (Map<String, Object>) usuario.get("rol");
        if (rolMap == null
                || !rolMap.get("nombre").toString().equalsIgnoreCase("Soporte y administrador de sistemas")) {
            throw new RuntimeException("Acceso denegado: el usuario no tiene el rol SOPORTE");
        }

        nuevasolicitud.setNombreusuario(nombreusuario);
        nuevasolicitud.setEmailusuario(correousuario);

        return solicitudSoporteRepository.save(nuevasolicitud);
    }

    /**
     * Obtiene todas las solicitudes de soporte registradas en la base de datos.
     *
     * @return Lista de objetos {@link Soporte}.
    */
    // Método para obtener todas las solicitudes de soporte (GET)
    public List<Soporte> obtenerTodasLasSolicitudes() {
        return solicitudSoporteRepository.findAll();
    }

    /**
     * Busca una solicitud de soporte por su ID.
     *
     * @param id Identificador único de la solicitud.
     * @return {@link Optional} con la solicitud si existe, vacío si no.
     */
    // Método para obtener una solicitud por ID (GET)
    public Optional<Soporte> obtenerSolicitudPorId(Long id) {
        return solicitudSoporteRepository.findById(id);
    }

    /**
     * Elimina una solicitud de soporte si existe.
     *
     * @param id ID de la solicitud a eliminar.
     * @return {@code true} si se eliminó correctamente, {@code false} si no se encontró.
     */
    public boolean eliminarSolicitud(Long id) {
        if (solicitudSoporteRepository.existsById(id)) {
            solicitudSoporteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }


    /**
     * Devuelve todas las solicitudes de soporte asociadas a un usuario específico.
     *
     * @param usuarioId ID del usuario.
     * @return Lista de solicitudes correspondientes a ese usuario.
    */
    public List<Soporte> obtenerSolicitudesPorUsuario(Long usuarioId) {
        return solicitudSoporteRepository.findByUsuarioId(usuarioId);
    }

}
