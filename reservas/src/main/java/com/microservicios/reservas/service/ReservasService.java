package com.microservicios.reservas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.reservas.model.Reservas;
import com.microservicios.reservas.repository.ReservaReposiry;
import com.microservicios.reservas.webclient.UsuarioClient;

@Service
public class ReservasService {

    @Autowired//crear una clase sin crearla
    private ReservaReposiry reservaReposiry;

    @Autowired
    private UsuarioClient usuarioClient;

   public List<Reservas> obtenerTodas(){

        return reservaReposiry.findAll();
        //evuelve una lista con todas las reservas guardadas en la base de datos

   }

   public Reservas crearReserva(Reservas nuevareserva){

    Map<String, Object> usuario = usuarioClient.getUsuarioById(nuevareserva.getUsuarioId());
        if (usuario == null || usuario.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado, no puede hacer la reserva ");
        }

        String nombre =(String ) usuario.get("nombres");

        nuevareserva.setNombrecliente(nombre);

        double preciofijo =150000;
        nuevareserva.setPrecio(preciofijo);


        return reservaReposiry.save(nuevareserva);


        
   }


    public void  eliminar(long id){
        reservaReposiry.deleteById(id);
        //Esa línea elimina la reserva con el id especificado de la base de datos
    }

    public Reservas obtenerporid(long id){
        return reservaReposiry.findById(id).orElse(null);
        //encontrar por id, si no lo encuentra tira null
    }
    

    
    

    
}
