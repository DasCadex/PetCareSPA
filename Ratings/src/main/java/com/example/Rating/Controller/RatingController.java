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

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    // {
    //     "idusuario": 1,
    //     "idproducto": 101,
    //     "rating": 5,

    @PostMapping
    public ResponseEntity<?> crearRating(@RequestBody Rating rating) {
        try {
            Rating nuevoRating = ratingService.guardarRating(rating);
            return new ResponseEntity<>(nuevoRating, HttpStatus.CREATED);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }



    }



    @GetMapping
    public ResponseEntity <List<Rating>> obtenerTodosLosRatings() {
        List<Rating> ratings = ratingService.obtenerTodosLosRatings();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    

    @GetMapping("/{id}")
    public ResponseEntity<Rating> obtenerRatingPorId(@PathVariable Long id) {
        Optional<Rating> rating = ratingService.obtenerRatingPorId(id);
        return rating.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }



    






}
