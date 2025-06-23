package com.microservicios.mascotas.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
            .info(
                new Info()
                .title("API Mascotas")
                .version("1.0")
                .description("Documentación de la API de mascotas"));
            
    }
}
