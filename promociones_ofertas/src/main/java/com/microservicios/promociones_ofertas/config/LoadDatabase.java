package com.microservicios.promociones_ofertas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservicios.promociones_ofertas.model.promocion;
import com.microservicios.promociones_ofertas.repository.promocionRepository;

@Configuration
public class LoadDatabase {


    @Bean
    CommandLineRunner inirDataBase(promocionRepository promocionRepository){
        return args -> {
            if (promocionRepository.count()== 0 ){

                promocion promo1 = new promocion(null, "Correa de perro", "2x1");
                promocion promo2= new promocion(null, "Comida de dato", "20% de descuento");

                promocionRepository.save(promo1);
                promocionRepository.save(promo2);

                System.out.println("Datos iniciales cargados en la base de datos.");
            }else{
                System.out.println("ℹYa existen productos en la base de datos. No se insertaron nuevos.");
            }
        };

    }
}
