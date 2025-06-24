package com.example.Rating.Controller;

import com.example.Rating.Model.Rating;
import com.example.Rating.Service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Operation(summary = "Crea un nuevo rating")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rating creado correctamente",
            content = @Content(schema = @Schema(implementation = Rating.class)))
    })
    @PostMapping
    public ResponseEntity<Rating> crearRating(@RequestBody Rating rating) {
        Rating nuevo = ratingService.guardarRating(rating);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene la lista de todos los ratings registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ratings obtenida con éxito",
            content = @Content(schema = @Schema(implementation = Rating.class)))
    })
    @GetMapping
    public ResponseEntity<List<Rating>> obtenerTodosLosRatings() {
        return new ResponseEntity<>(ratingService.obtenerTodosLosRatings(), HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un rating específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rating encontrado correctamente",
            content = @Content(schema = @Schema(implementation = Rating.class))),
        @ApiResponse(responseCode = "404", description = "Rating no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Rating> obtenerRatingPorId(@PathVariable Long id) {
        return ratingService.obtenerRatingPorId(id)
                .map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Elimina un rating por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Rating eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rating no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRating(@PathVariable Long id) {
        try {
            ratingService.eliminarRaiting(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualiza parcialmente un rating existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rating actualizado con éxito",
            content = @Content(schema = @Schema(implementation = Rating.class))),
        @ApiResponse(responseCode = "404", description = "Rating no encontrado")
    })
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

    @Operation(summary = "Obtiene todos los ratings realizados por un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ratings del usuario obtenidos con éxito",
            content = @Content(schema = @Schema(implementation = Rating.class)))
    })
    @GetMapping("/usuario/{idusuario}")
    public ResponseEntity<List<Rating>> obtenerRatingsPorUsuario(@PathVariable Long idusuario) {
        List<Rating> resultados = ratingService.obtenerPorUsuario(idusuario);
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todos los ratings realizados a un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ratings del producto obtenidos con éxito",
            content = @Content(schema = @Schema(implementation = Rating.class)))
    })
    @GetMapping("/producto/{idproducto}")
    public ResponseEntity<List<Rating>> obtenerRatingsPorProducto(@PathVariable Long idproducto) {
        List<Rating> resultados = ratingService.obtenerPorProducto(idproducto);
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

}
