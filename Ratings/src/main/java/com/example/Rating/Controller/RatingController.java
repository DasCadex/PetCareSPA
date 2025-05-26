package com.example.Rating.Controller;

import com.example.Rating.Model.Rating;
import com.example.Rating.Service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {



    @Autowired
    private RatingService ratingService;

      @PostMapping
    public ResponseEntity<Rating> crearRating(@RequestBody Rating rating) {
        Rating nuevo = ratingService.guardarRating(rating);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // ✅ GET - Obtener todos los ratings
    @GetMapping
    public ResponseEntity<List<Rating>> obtenerTodosLosRatings() {
        return new ResponseEntity<>(ratingService.obtenerTodosLosRatings(), HttpStatus.OK);
    }

    // ✅ GET - Obtener rating por ID
    @GetMapping("/{id}")
    public ResponseEntity<Rating> obtenerRatingPorId(@PathVariable Long id) {
        return ratingService.obtenerRatingPorId(id)
                .map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ✅ DELETE - Eliminar rating por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRating(@PathVariable Long id) {
        try {
            ratingService.eliminarRaiting(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Rating> actualizarParcialRating(@PathVariable Long id, @RequestBody Rating cambios) {
    return ratingService.obtenerRatingPorId(id).map(ratingExistente -> {
        if (cambios.getRating() != null) {
            ratingExistente.setRating(cambios.getRating());
        }
        Rating actualizado = ratingService.guardarRating(ratingExistente);
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
}



  


}
