package com.example.Rating.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Rating.Model.Rating;
import com.example.Rating.Repository.RatingRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RatingService {

    @Autowired

    private final RatingRepository ratingRepository;
    private final ProductoClient productoClient;//LO DEL PRODUCTO BRO

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
        this.productoClient = productoClient; //AQUI IRIA LO DEL PRODUCTO
    }

    public Rating guardarRating(Rating rating){
    if (rating.getRating() == null || rating.getRating() < 1 || rating.getRating() > 5) {
        throw new IllegalArgumentException("La Calificacion debe ser un numero entre 1 y 5");    
        
    }
    if (rating.getIdusuario() == null) {
        throw new IllegalArgumentException("El ID del usuario no puede ser nulo.");

    }
    if (rating.getIdproducto() == null){
        throw new IllegalArgumentException("El ID del Producto no puede ser nulo.");

    }

    try{

        Map<String, Object> productoDetails = productoClient.getProductoById(rating.getIdproducto());
        if (productoDetails == null || productoDetails.isEmpty()) {
            throw new IllegalArgumentException(
                    "El producto con ID " + rating.getIdproducto() + " no fue encontrado en el servicio de productos.");
        }
        System.out.println("Producto validado: " + productoDetails.get("nombre"));
    }catch(
        
    RuntimeException e)
    {

        throw new IllegalArgumentException("No se pudo validar el producto: " + e.getMessage(), e);
    }

    return ratingRepository.save(rating);
    }

    

    public List<Rating> obtenerTodosLosRatings() {
        return ratingRepository.findAll();

    }

    public Optional<Rating> obtenerRatingPorId(Long id) {
        return ratingRepository.findById(id);
    }

}
