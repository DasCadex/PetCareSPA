package com.example.Soporte.Repository;

import com.example.Soporte.Model.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Long> {
   
}
