package com.example.Notificaciones.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Notificaciones.Model.Notificaciones;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {
    List<Notificaciones> findByLeidaFalse(); 
    List<Notificaciones> findByIdpago(Long idpago);

}
