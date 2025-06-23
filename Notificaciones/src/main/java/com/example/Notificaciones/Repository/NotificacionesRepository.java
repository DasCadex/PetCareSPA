package com.example.Notificaciones.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Notificaciones.Model.Notificaciones;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {

}
