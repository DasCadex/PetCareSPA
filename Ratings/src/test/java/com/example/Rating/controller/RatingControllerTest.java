package com.example.Rating.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Rating.Controller.RatingController;
import com.example.Rating.Model.Rating;
import com.example.Rating.Service.RatingService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    // inyectamos el mock en el service

    @MockBean
    private RatingService ratingService;

    // creamos el mock que nos da spring

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getall_returnTodosLosRating() {
        List<Rating> mockList = Arrays.asList(new Rating(1L, 2L, 3L, 5, "no echarme fullstack"));

        when(ratingService.obtenerTodosLosRatings()).thenReturn(mockList);

        try {
            mockMvc.perform(get("/api/ratings"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].idopinion").value(1L));

        } catch (Exception e) {

        }

    }

    @Test
    void deleteById_returnPetar() {
        // no es necesario crear un rating de producto solo asignanomo una ide y que la
        // borre

        Long idopinion = 1L;

        try {
            mockMvc.perform(delete("/api/ratings/{id}", idopinion)).andExpect(status().isNoContent());

        } catch (Exception e) {

        }
    }

    @Test
    void gettByid_returnPorId() {
        Long id = 1L;
        Rating mockRating = new Rating(1L, 2L, 3L, 5, "comentario");

        when(ratingService.obtenerRatingPorId(id)).thenReturn(Optional.of(mockRating));

        try {
            mockMvc.perform(get("/api/ratings/{id}", id)).andExpect(status().isOk());
        } catch (Exception e) {

        }
    }

    @Test
    void postRating_returnCreated() {
        // creamo un rating fictisio
        Rating nuevoRating = new Rating(1L, 2L, 3L, 5, "comentario");
        // el any rating.class ara que permita recibir cualquier tipo de rating y ara lo
        // que se le ordene
        when(ratingService.guardarRating(any(Rating.class))).thenReturn(nuevoRating);

        try {
            mockMvc.perform(post("/api/ratings")
                    .contentType("application/json")
                    .content("""
                                {
                                    "idusuario": 2,
                                    "idproducto": 3,
                                    "rating": 5,
                                    "productoDes": "comentario"
                                }
                            """))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
        }
    }

    @Test
    void patchRating_returnOk() {
        Long id = 1L;
        Rating existente = new Rating(id, 2L, 3L, 5, "comentario");
        Rating actualizado = new Rating(id, 2L, 3L, 4, "comentario");

        when(ratingService.obtenerRatingPorId(id)).thenReturn(Optional.of(existente));
        when(ratingService.guardarRating(any(Rating.class))).thenReturn(actualizado);

        try {
            mockMvc.perform(patch("/api/ratings/{id}", id)
                    .contentType("application/json")
                    .content("{\"rating\": 4}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            
        }
    }

    @Test
    void getByUsuarioId_returnOk() {
        Long idusuario = 2L;

        List<Rating> mockList = List.of(new Rating(1L, 2L, 3L, 5, "comentario"));

        when(ratingService.obtenerPorUsuario(idusuario)).thenReturn(mockList);

        try {
            mockMvc.perform(get("/api/ratings/usuario/{idusuario}", idusuario))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            
        }
    }

}
