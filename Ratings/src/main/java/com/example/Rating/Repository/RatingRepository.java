package com.example.Rating.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Rating.Model.Rating;

@Repository
public interface RatingRepository extends JpaRepository <Rating , Long>{
    List<Rating> findByIdusuario(Long idusuario);
    List<Rating> findByIdproducto(Long idproducto);

}
