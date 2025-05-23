package com.example.productos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;

@Configuration
public class LoadDatabase {

    
    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepo) {
        return args -> {
            if (productoRepo.count() == 0) {
                // Crear productos iniciales con constructor completo
                Producto correaPerro = new Producto(null, 10, "Correa de perro", "MarcaCanina", 5000);
                Producto comidaGato = new Producto(null, 16, "Comida para gato 5 kg", "Pedigri", 10000);

                // Guardarlos en la base de datos
                productoRepo.save(correaPerro);
                productoRepo.save(comidaGato);

                System.out.println("Datos iniciales cargados en la base de datos.");
            } else {
                System.out.println("ℹYa existen productos en la base de datos. No se insertaron nuevos.");
            }
        };
    }
}
