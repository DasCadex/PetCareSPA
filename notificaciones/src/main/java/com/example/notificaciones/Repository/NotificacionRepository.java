package com.example.notificaciones.Repository;

import com.example.notificaciones.Model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
}
