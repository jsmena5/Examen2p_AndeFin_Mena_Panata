package com.andesfin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI andesFinOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AndesFin API")
                        .description("API REST para simulaciones de microinversiones de la fintech AndesFin")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo AndesFin")
                                .email("soporte@andesfin.com"))
                        .license(new License()
                                .name("Uso académico")
                                .url("https://andesfin.com")));
    }
}