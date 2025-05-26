package com.example.Rating.Service;

import java.util.List;
import java.util.Optional;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Rating.Model.Rating;
import com.example.Rating.Repository.RatingRepository;
import com.example.Rating.WebClient.ProductoClient;
import com.example.Rating.WebClient.UsuarioClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RatingService {

    @Autowired
    private  RatingRepository ratingRepository;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private UsuarioClient usuarioClient;

    public Rating guardarRating(Rating nuevorating){

        Map<String, Object> usuarioraiting= usuarioClient.getUsuarioById(nuevorating.getIdusuario());

        if( usuarioraiting== null || usuarioraiting.isEmpty()){
            throw new RuntimeException("Usuario no encontrado o inexistente");
        }

        Map<String, Object> productoraiting = productoClient.getProductoById(nuevorating.getIdproducto());

        if (productoraiting== null  || productoraiting.isEmpty()){
            throw new RuntimeException("producto no encontrado para calificar ");
        }


        String nomproducto = (String) productoraiting.get("nombre_producto");

        if (nomproducto == null){
            throw new RuntimeException("Nombre del producto no encontrado ");
        }

        nuevorating.setNombreproducto(nomproducto);





        return ratingRepository.save(nuevorating);

    }
    

 
   

    

    public List<Rating> obtenerTodosLosRatings() {
        return ratingRepository.findAll();

    }

    public Optional<Rating> obtenerRatingPorId(Long id) {
        return ratingRepository.findById(id);
    }

    public void eliminarRaiting(Long id){

        Rating rating= ratingRepository.findById(id).orElseThrow(() -> new RuntimeException("comentario no encontrado"));
        
        ratingRepository.delete(rating);

    }

}
