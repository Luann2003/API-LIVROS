package com.apilivros.apilivros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition
@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
public class OpenApiConfig {

    @Bean
    public OpenAPI dsmovieAPI() {
        return new OpenAPI()
           .info(new Info()
           .title("book API")
           .description("Book Reference Project")
           .version("v0.0.1")
           .license(new License()
           .name("Apache 2.0")
           .url("https://github.com/Luann2003/API-LIVROS")));
    }
}