package com.microservicios.Reservas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.Reservas.model.Reservas;
import com.microservicios.Reservas.repository.ReservaReposiry;

@Service
public class ReservasService {

    @Autowired//crear una clase sin crearla
    private ReservaReposiry reservaReposiry;

   public ReservasService(ReservaReposiry reservaReposiry){

        this.reservaReposiry = reservaReposiry;

   }

   public List<Reservas> obtenerTodas(){

        return reservaReposiry.findAll();
        //evuelve una lista con todas las reservas guardadas en la base de datos

   }

   public Reservas crearReserva(Reservas reserva){
        return reservaReposiry.save(reserva);
       // crear nueva reserva
   }


    public void  eliminar(long id){
        reservaReposiry.deleteById(id);
        //Esa línea elimina la reserva con el id especificado de la base de datos
    }

    public Reservas obtenerporid(long id){
        return reservaReposiry.findById(id).orElse(null);
        //encontrar por id, si no lo encuentra tira null
    }

    public List<Reservas> obtenerporestado(String estado){
        return reservaReposiry.findByEstado(estado);
        //para ver reservas pendiente, confirmada , en proceso , etc
    }

    public List<Reservas> obtenerPorEstado(String estado) {
        return reservaReposiry.findByEstado(estado);
        //para ver reservas pendiente, confirmada , en proceso , etc
    }
}
